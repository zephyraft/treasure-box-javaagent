_default:
    @just --list -u

agent:
    java -cp build/classes/java/test \
        -javaagent:/Users/zhonghaoyuan/IdeaProjects/treasure-box-javaagent/build/libs/treasure-box-javaagent-1.0-SNAPSHOT.jar \
        zephyr.agent.ApiTest

