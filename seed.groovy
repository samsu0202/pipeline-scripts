def VSaaSPlatforms = ['Amba', 'SStar']

pipeline {
    agent any 
    stages {
        stage('Pipeline to seed or update all pipelines') {
            steps {
                script {
                    $VSaaSPlatforms.each { platform ->
                        echo $platform
                    }
                }
                // jobDsl  targets: ['*.groovy'].join('\n')
//                 echo 123
            }
        }
    }
}
