def call(){


    pipeline {
        agent any
        parameters { 
            //choice(name: 'eleccion', choices: ['gradle', 'maven'], description: 'Invocacion ')
            // Define el o los stage a ejecutar
            string(name: 'stage', defaultValue: '', description: 'Stage a ejecutar') 
        }
            
        stages {
            stage('Validar Stage') {
                steps{
                    script{

                        /*LLAMAR A FUNCION QUE VALIDE SI LOS STAGE INGRESADOS POR EL USUARIO SON VALIDOS
                        INPUT: STRING CON STAGE INGRESADOS
                        OUTPUT: BOLEANO
                        */

                        util.validarStages()

                    }
                }
            }
            stage('Validando Ramas') {
                when {
                        branch 'feature-estadopais'
                    }
                    steps{
                        script{
                        
                            //Validar formato de nombre de rama release según patrón release-v{major}-{minor}-{patch}
                            //Si Rama == Release entonces llamar a funcion que valide el nombre segun patron
                            
                            util.validarNombre()
                        }
                    }
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