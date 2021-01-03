def call(){

    echo "Estoy dentro del CI"

    stage('Build'){
        sh './gradlew clean build'
    }

    /*

    - compile
        Compilar el código con comando maven.
    - unitTest
        Testear el código con comando maven.
    - jar
        Generar artefacto del código compilado
    - sonar
        Generar análisis con sonar para cada ejecución
        Cada ejecución debe tener el siguiente formato de nombre:
        {nombreRepo}-{rama}-{numeroEjecucion} ejemplo: ms-iclab-feature-estadomundial-10
    - nexusUpload
        Subir artefacto creado al repositorio privado de Nexus.
        Ejecutar este paso solo si los pasos anteriores se ejecutan de manera correcta.
    - gitCreateRelease
        Crear rama release cuando todos los stages anteriores estén correctamente ejecutados.
        Este stage sólo debe estar disponible para la rama develop.

    */
}

return this;