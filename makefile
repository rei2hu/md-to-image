test:
	javac HTMLImgDemo.java
	java -Djava.awt.headless=true HTMLImgDemo
clean:
	rm *.class
	rm */*.class
	
