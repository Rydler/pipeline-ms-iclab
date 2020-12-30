

def validarStages(){
    
    echo "Funcion Validando Stages"
}

def validarRamas(){
    echo "Funcion Validando Ramas"
}

def validarNombre(){
    
    echo "Funcion Validando Nombre de Rama Release"
}

def validaTecnologia(){
    
    echo "Funcion Validando tecnologia"
}

def validaArchivo(){
    
    if (fileExists('mvnw.cmd')) {
        echo 'Existe el Archivo para la aplicacion en Gradle'
    } else {
        echo 'No Existe el Archivo para la aplicacion en Gradle'
    }
}