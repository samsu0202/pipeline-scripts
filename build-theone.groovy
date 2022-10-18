pipeline {
  agent any
  stages {
    stage("Clone source") {
      steps {
        dir('source') {
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
