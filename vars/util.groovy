

def validarStages(){
    
    echo "Funcion Validando Stages"
}

def validarRamas(){
    echo "Funcion Validando Ramas"
    echo "${BRANCH_NAME}"
    if ("${BRANCH_NAME}" == 'feature' || "${BRANCH_NAME}" == 'develop' || "${BRANCH_NAME}" == 'release') {
        echo "RAMA CORRECTA"
    } else {
        echo "RAMA INCORRECTA"
    }
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