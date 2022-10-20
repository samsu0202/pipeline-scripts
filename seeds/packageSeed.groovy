final PLATFORMS = ['Amba', 'SStar']
folder('packages') {
    displayName('Packages')
    description('Folder for packages')
}
PLATFORMS.each { platform ->
    pipelineJob("packages/Theone-$platform-test") {
        definition {
            cps {
                script(readFileFromWorkspace('build-theone.groovy'))
                sandbox()     
            }
//             cpsScm {
//                 scm {
//                     git {
//                         remote {
//                             github('samsu0202/pipeline-scripts')
//                         }
//                     }
//                 }
//                 scriptPath('build-theone.groovy')
//             }
        }
        parameters {
            stringParam('platform', "$platform", "$platform")
        }
    }
}
