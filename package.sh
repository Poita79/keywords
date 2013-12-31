#!/usr/bin/env sh
set -e

scala_version=2.10
sbt_version=0.13
project_name=keywords
artifacts_dir=target/scala-${scala_version}
release_props=${artifacts_dir}/release.properties
build_number="${BUILD_NUMBER:-dev}"
release_name=${project_name}_${scala_version}-${build_number}
release_jar=${release_name}.jar
release_pom=${release_name}.pom

echo "version := \"${build_number}\"" > build-number.sbt

chmod +x ./sbt.sh
./sbt.sh clean package makePom

echo "release.files=${release_jar},${release_pom}" >> ${release_props}
echo "release.path=com/github/petekneller/${project_name}_${scala_version}/${build_number}/" >> ${release_props}
echo "${release_jar}.labels=Jar" >> ${release_props}
