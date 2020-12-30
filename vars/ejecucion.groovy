def call(){

    
    pipeline {
        agent any
        parameters { 
            //choice(name: 'eleccion', choices: ['gradle', 'maven'], description: 'Invocacion ')
            // Define el o los stage a ejecutar
            string(name: 'stage', defaultValue: '', description: 'Stage a ejecutar') 
        }
            
        stages {
            stage('Pipeline') {
                steps{
                    script{

                        echo "RAMA: ${BRANCH_NAME}"
                        /*LLAMAR A FUNCION QUE VALIDE SI LOS STAGE INGRESADOS POR EL USUARIO SON VALIDOS
                        INPUT: STRING CON STAGE INGRESADOS
                        OUTPUT: BOLEANO
                        */


                        /*VALIDAR TIPO DE RAMA A EJECUTAR
                        when {
                            branch 'production'
                            echo "When"
                        }
                        */

                        // println 'Stage a ejecutar': + params.stage
                        //println 'Herramienta de ejecución: ' + params.eleccion
                        //echo "Stage: ${params.stage}"

                    }
                }
            }
            stage('Stage When')
            when {
                    branch 'feature-estadopais'
                }
                steps{
                    echo "Aca entre al When"
                }
        }

        post {
            failure {
                slackSend channel: 'U01DRJVPRTK', color: 'good', message: "Build Failure: [Daniel Morales][${env.JOB_NAME}][${params.CHOICES}] Ejecución fallida en stage [${env.STAGE_NAME}]", teamDomain: 'dipdevopsusach2020', tokenCredentialId: 'Slack_integration'
            }
            success {
                slackSend channel: 'U01DRJVPRTK', color: 'good', message: "Build Success: [Daniel Morales][${env.JOB_NAME}][${params.CHOICES}] Ejecución exitosa.", teamDomain: 'dipdevopsusach2020', tokenCredentialId: 'Slack_integration'

            }
        }
    }
}

return this;