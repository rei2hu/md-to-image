test:
	javac HTMLImgDemo.java
	java HTMLImgDemo ./tests/test.html
	mv test.jpg $(DESKTOP)
clean:
	rm *.class
	rm */*.class
	
