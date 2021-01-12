

def validarStages(){

    def arrayStage =  params.stage.split(';')
    echo "Funcion Validando Stages"
    def aux = 0
    def rama_value = "${BRANCH_NAME}"
    def validStageCI = ['Compile, Test & Jar', 'Sonar', 'nexusUpload', 'gitCreateRelease']
    def validStageCD = ['Build', 'Sonar', 'Run', 'Test', 'Nexus']

    if (rama_value.startsWith('feature') || rama_value.startsWith('develop')){
        echo "VALIDANDO STAGES DE CI"
        for (String validValues: validStageCI){
            for (String values: arrayStage){
                if (values == validValues){
                    aux += 1
                }
            }
        }
        if (aux == arrayStage.size()){
            echo "STAGES CI VALIDOS"
        }
        else{
            error "STAGES CI INVALIDOS"
        }
    }
    else if (rama_value.startsWith('release')){
        echo "VALIDANDO STAGES DE CD"
    }
}


def validarRamas(){
    echo "Funcion Validando Ramas"
    def rama_value = "${BRANCH_NAME}"
    return  (rama_value.startsWith('feature') || rama_value.startsWith('develop') || rama_value.startsWith('release'))   
}

def validarNombre(){
    
    echo "Funcion Validando Nombre de Rama Release"
}

def validaTecnologia(){
    
    echo "Funcion Validando tecnologia"
}

def validaArchivo(){
    
    if (fileExists('build.gradle')) {
        echo 'Existe el Archivo para la aplicacion en Gradle'
    } else {
        echo 'No Existe el Archivo para la aplicacion en Gradle'
    }
}

def determineRepoName() {
    return scm.getUserRemoteConfigs()[0].getUrl().tokenize('/').last().split("\\.")[0]
}