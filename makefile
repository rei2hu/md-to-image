test:
	javac HTMLImgDemo.java
	java HTMLImgDemo test.html
	mv test.jpg $(DESKTOP)
clean:
	rm *.class
	rm */*.class
	
