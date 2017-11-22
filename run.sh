function run () {
  export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home/
  gradle --no-daemon clean && gradle --no-daemon test
  export JAVA_HOME=/Applications/zulu8.25.0.1-jdk8.0.152-macosx_x64
  gradle --no-daemon clean && gradle --no-daemon test
#	export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0.jdk/Contents/Home/
#	gradle --no-daemon clean && gradle --no-daemon test
#	export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/	
#	gradle --no-daemon clean && gradle --no-daemon test
}
while :
do
  run
done
