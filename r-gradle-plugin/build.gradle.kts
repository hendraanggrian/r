plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    dokka
    `git-publish`
    bintray
    `bintray-release`
}

group = RELEASE_GROUP
version = RELEASE_VERSION

sourceSets {
    get("main").java.srcDir("src")
    get("test").java.srcDir("tests/src")
}

gradlePlugin {
    (plugins) {
        register(RELEASE_ARTIFACT) {
            id = RELEASE_GROUP
            implementationClass = "$RELEASE_GROUP.RPlugin"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib", VERSION_KOTLIN))
    compile(javapoet())

    testImplementation(kotlin("test", VERSION_KOTLIN))
    testImplementation(junit())
}

ktlint()
deployable()

tasks {
    val dokka by existing(org.jetbrains.dokka.gradle.DokkaTask::class) {
        get("gitPublishCopy").dependsOn(this)
        outputDirectory = "$buildDir/docs"
        doFirst {
            file(outputDirectory).deleteRecursively()
            buildDir.resolve("gitPublish").deleteRecursively()
        }
    }
    gitPublish {
        repoUri = RELEASE_WEBSITE
        branch = "gh-pages"
        contents.from(dokka.get().outputDirectory)
    }
}

publish {
    bintrayUser = BINTRAY_USER
    bintrayKey = BINTRAY_KEY
    dryRun = false
    repoName = RELEASE_REPO

    userOrg = RELEASE_USER
    groupId = RELEASE_GROUP
    artifactId = RELEASE_ARTIFACT
    publishVersion = RELEASE_VERSION
    desc = RELEASE_DESC
    website = RELEASE_WEBSITE
}