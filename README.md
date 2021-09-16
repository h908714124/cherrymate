Find base commit of two branches

````sh
./gradlew clean run --args='--root-dir . --source-branch develop_3.x --target-branch develop_4.x BASE'
````

Find commits that are in `develop_3.x` but not in `develop_4.x` (forgot cherry-pick?)

````sh
./gradlew clean run --args='--root-dir . --source-branch develop_3.x --target-branch develop_4.x COMPARE'
````
