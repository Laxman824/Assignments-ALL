compile:
	javac Bases.java
	javac Node.java
	javac Queue.java
	javac PriorityQueue.java
	javac Item.java
	javac Buyer.java
	javac Seller.java
	javac Assignment2Driver.java

run:
	java Assignment2Driver tests/input.txt > tests/output.txt

clean:
	rm *.class

handin:	
	@mkdir src
	@cp *.java src/
	@zip -r $(ENTRY)_assignment2.zip src/
	@base64 $(ENTRY)_assignment2.zip > $(ENTRY)_assignment2.zip.b64
	#@rm $(ENTRY)_assignment2.zip
