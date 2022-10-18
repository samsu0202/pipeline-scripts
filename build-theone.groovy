pipeline {
  agent any
  environment {
    SOURCE_DIR = "${env.WORKSPACE}/source"
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
          git credentialsId: 'vault-git-sdd-aws-acuity', branch:'main', url: 'https://sdd-gitlab.vivotek.tw/aws-acuity/ci-test.git'
          // replace submodules from ssh to https because we use deploy token for CI
          sh """
            sed -i 's#git@#https://#g;s#:aws-acuity#/aws-acuity#g' .gitmodules
            git submodule sync
            git submodule update --init --recursive
          """
        }
      }
    }
  }
}
