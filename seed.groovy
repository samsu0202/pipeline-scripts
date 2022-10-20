def VSAAS_PLATFORMS = ['Amba', 'SStar']

pipeline {
    agent any 
    stages {
        stage('Pipeline to seed or update all pipelines') {
            steps {
                script {
                    VSAAS_PLATFORMS.each { platform ->
                        echo platform
                    }
                }
                // jobDsl  targets: ['*.groovy'].join('\n')
//                 echo 123
            }
        }
    }
}
