def call(){

    echo "Estoy dentro del CI"
    echo "REPOSITORIO ==> "
    echo "RAMA ==> ${BRANCH_NAME}"
    echo "NºEJECUCION ==> ${EXECUTOR_NUMBER}"



    //Compila, Testea y Genera el Jar usando gradle
    stage('Compile, Test & Jar'){
        sh './gradlew clean build'
    }

    /*
    - unitTest
        Testear el código con comando maven.
    */


    /*
    - sonar
        Generar análisis con sonar para cada ejecución
        Cada ejecución debe tener el siguiente formato de nombre:
        {nombreRepo}-{rama}-{numeroEjecucion} ejemplo: ms-iclab-feature-estadomundial-10
    */

    stage('Sonar'){
        def scannerHome = tool 'sonar';
        withSonarQubeEnv('sonar') { // If you have configured more than one global server connection, you can specify its name
            sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-prueba -Dsonar.java.binaries=build"
        }
    }

    // Al ser secuencial, este paso de Nexus se ejecuta si los anteriores se ejecutaron de forma correcta.
    // Fijarse en el Filepath para ir a buscar el artefacto.
    stage('nexusUpload'){
            nexusPublisher nexusInstanceId: 'Nexus_server_local', nexusRepositoryId: 'test-repo', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: '/var/lib/jenkins/workspace/anch-ms-iclab_feature-estadopais/build/libs/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '1.0.5']]]
        }

    
    
    
    /*
        - unitTest
        Testear el código con comando maven.
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