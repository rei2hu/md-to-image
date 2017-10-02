test:
	javac HTMLImgDemo.java
	java -Djava.awt.headless=true HTMLImgDemo ./tests/test.html
clean:
	rm *.class
	rm */*.class
	
