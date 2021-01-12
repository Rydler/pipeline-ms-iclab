def call(){

    
    pipeline {
        agent any
        parameters { 
            //choice(name: 'eleccion', choices: ['gradle', 'maven'], description: 'Invocacion ')
            // Define el o los stage a ejecutar
            string(name: 'stage', defaultValue: '', description: 'Stage a ejecutar') 
        }
            
        stages {
            stage('Validaciones') {
                steps{
                    script{

                        // VALIDA SI LOS STAGE INGRESADOS POR EL USUARIO SON VALIDOS. INPUT: STRING CON STAGE INGRESADOS. OUTPUT: BOLEANO
                        util.validarStages()
                        
                        // VALIDA QUE LAS RAMA A PROCESAR SEA VALIDA. INPUT: NOMBRE RAMA. OUTPUT BOLEANO
                        if (util.validarRamas()){} else {error "El nombre de la Rama no es valido"}
                        
                        // VALIDA FORMATO NOMBRE RAMA RELEASE
                        if ("${BRANCH_NAME}" == 'release'){
                            util.validarNombre()
                        }

                        // VALIDA TECNOLOGIA UTILIZADA
                        util.validaTecnologia()
                        // VALIDA ARCHIVO EXISTENTE
                        util.validaArchivo()
                      
                    }
                }
            }

            stage('Branch Feature') {
                when {
                    branch 'feature-*'
                }    
                steps{
                    script{

                        echo "NOMBRE RAMA: ${BRANCH_NAME}"
                        // INTEGRACION CONTINUA
                        //ci.call() 
                    }
                }
            }

            stage('Branch Develop') {
                when {
                    branch 'develop'
                } 
                steps{
                    script{

                        echo "NOMBRE RAMA: ${BRANCH_NAME}"
                        // INTEGRACION CONTINUA
                        //ci.call()
                    }
                } 
            }

            stage('Branch Release') {
                when {
                    branch 'release'
                } 
                steps{
                    script{
                        
                        echo "NOMBRE RAMA: ${BRANCH_NAME}"
                        // VALIDAR NOMBRE SEGUN PATRON release-v{major}-{minor}-{patch}
                        util.validarNombre()
                        // Despliegue continuo
                        //cd.call()
                        
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