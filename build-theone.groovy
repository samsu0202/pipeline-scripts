pipeline {
  agent any
  environment {
    SOURCE_DIR = "${env.WORKSPACE}/source"
    DOCKER_MOUNT_SOURCE_DIR = "/data/source"
  }
  parameters {
    booleanParam(name: 'cleanSource', defaultValue: false, description: 'Clean the source code folder')
  }
  stages {
    stage('test') {
      steps {
        echo params.platform
      }
    }
    stage('Clean source') {
      when {
        expression { params.cleanSource }
      }
      steps {
        sh "rm -rf ${env.SOURCE_DIR}"
      }
    }
    stage('Clone source') {
      steps {
        dir("${env.SOURCE_DIR}") {
          git credentialsId: 'vault-git-sdd-aws-acuity', branch:'vssd', url: 'https://sdd-gitlab.vivotek.tw/aws-acuity/theone.git'
          // replace submodules from ssh to https because we use deploy token for CI
          sh """
            sed -i 's#git@#https://#g;s#:aws-acuity#/aws-acuity#g' .gitmodules
            git submodule sync
            git submodule update --init --recursive
          """
        }
      }
    }
    stage('Build') {
      agent {
        docker {
          image 'theone-build:latest'
          registryUrl "${env.PRIVATE_DOCKER_REGISTRY_SERVER}"
          args """-v /etc/group:/etc/group:ro 
            -v /etc/passwd:/etc/passwd:ro 
            -v /etc/shadow:/etc/shadow:ro 
            -v ${env.SOURCE_DIR}/:${env.DOCKER_MOUNT_SOURCE_DIR}"""
          }
      }
      steps {
        script {
          def targetOpt = -1
          if (params.platform == 'Amba') {
            targetOpt = 12
          } else if (params.platform == 'SStar') {
            targetOpt = 11
          } else {
            error("Platfom ${params.platform} not supported")
          }
          
          sh """
            cd ${env.DOCKER_MOUNT_SOURCE_DIR}
            make ${targetOpt}_default
            make all
          """
        }
      }
    }
  }
}
