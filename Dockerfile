FROM fabric8/java-centos-openjdk11-jre
ADD target/*.jar /home/hoover.jar
CMD ["java", "-jar", "/home/hoover.jar"]



