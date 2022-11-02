JAVAC ?= javac
JAVA ?= java
INPUT_FILE ?= INP
EXPECTED_OUTPUT_FILE ?= EXPECTED_OUTPUT
SCORE_FILE ?= score
score=2

logtrieerror= (echo 'ERROR '; echo 'EXPECTED_OUTPUT:'; echo "--------------"; cat Trie/$(EXPECTED_OUTPUT_FILE); echo "==============" ;echo "Your OUTPUT"; echo "--------------"; cat Trie/tmp;)
logrberror = (echo "ERROR"; echo "EXPECTED_OUTPUT:"; echo "--------------"; cat RedBlack/$(EXPECTED_OUTPUT_FILE); echo "==============" ;echo "Your OUTPUT:"; echo "--------------"; cat RedBlack/tmp;)
logpqerror = (echo 'ERROR '; echo 'EXPECTED_OUTPUT:'; echo "--------------"; cat PriorityQueue/$(EXPECTED_OUTPUT_FILE); echo "=============="; echo "Your OUTPUT:"; echo "--------------"; cat PriorityQueue/tmp; )
logpmerror = (echo 'ERROR '; echo 'EXPECTED_OUTPUT:'; echo "--------------"; cat ProjectManagement/$(EXPECTED_OUTPUT_FILE); echo "=============="; echo "Your OUTPUT:"; echo "--------------"; cat ProjectManagement/tmp; )

all: triebuild triecheck rbtreebuild rbtreecheck pqbuild  pqcheck pmbuild pmcheck clean
testcase: pqtestcase rbtestcase trietestcase pmtestcase
build: triebuild rbtreebuild pqbuild pmbuild
check: triecheck pqcheck rbtreecheck pmcheck
trie: triebuild triecheck trieclean
rbtree: rbtreebuild rbtreecheck rbtreeclean
pq: pqbuild pqcheck pqclean
pm: pmbuild pmcheck pmclean


triebuild:
	@echo "**********"
	@echo "Building Trie"
	@echo "**********"
	@$(JAVAC) -encoding ISO-8859-1 Trie/*.java
triecheck: triebuild
	@echo "**********"
	@echo "Checking Trie"
	@echo "**********"
	@$(JAVA) Trie.TrieDriverCode Trie/$(INPUT_FILE) >Trie/tmp
	@cd Trie &&	sed -i 's/[^[:print:]]//g' tmp &&	sed -i 's/[^[:print:]]//g' $(EXPECTED_OUTPUT_FILE)
	@diff -i Trie/tmp Trie/$(EXPECTED_OUTPUT_FILE) && echo 'TRIE SUCCESS'  || $(call logtrieerror)
trieclean:
	-@rm Trie/tmp -f
	-@rm Trie/*.class -f

rbtreebuild:
	@echo "\n**********"
	@echo "Building Red-Black Tree"
	@echo "**********"
	@$(JAVAC) -encoding ISO-8859-1 RedBlack/*.java
rbtreecheck: rbtreebuild
	@echo "\n**********"
	@echo "Checking Red-Black"
	@echo "**********"
	@$(JAVA) RedBlack.RedBlackDriverCode RedBlack/$(INPUT_FILE) >RedBlack/tmp
	@cd RedBlack && sed -i 's/[^[:print:]]//g' tmp && sed -i 's/[^[:print:]]//g' $(EXPECTED_OUTPUT_FILE)
	@diff -i RedBlack/tmp RedBlack/$(EXPECTED_OUTPUT_FILE) && echo 'RBTREE SUCCESS' || $(call logrberror)
rbtreeclean:
	-@rm RedBlack/tmp -f 
	-@rm RedBlack/*.class -f

pqbuild:
	@echo "\n**********"
	@echo "Building PriorityQueue"
	@echo "**********"
	@$(JAVAC) -encoding ISO-8859-1 PriorityQueue/*.java
pqcheck: pqbuild
	@echo "\n**********"
	@echo "Checking PriorityQueue"
	@echo "**********"
	@$(JAVA) PriorityQueue.PriorityQueueDriverCode PriorityQueue/$(INPUT_FILE) >PriorityQueue/tmp
	@cd PriorityQueue  && sed -i 's/[^[:print:]]//g' tmp && sed -i 's/[^[:print:]]//g' $(EXPECTED_OUTPUT_FILE)
	@diff -i PriorityQueue/tmp PriorityQueue/$(EXPECTED_OUTPUT_FILE) && echo 'PQ SUCCESS' || $(call logpqerror)
pqclean:
	-@rm PriorityQueue/tmp -f
	-@rm PriorityQueue/*.class -f

pmbuild:
	@echo "\n**********"
	@echo "Building ProjectManagement"
	@echo "**********"
	@$(JAVAC) -encoding ISO-8859-1 ProjectManagement/*.java
pmcheck: pmbuild
	@echo "\n**********"
	@echo "Checking ProjectManagement"
	@echo "**********"
	@$(JAVA) ProjectManagement.Scheduler_Driver ProjectManagement/$(INPUT_FILE) >ProjectManagement/tmp
	@cd ProjectManagement && sed -i 's/[^[:print:]]//g' tmp && sed -i 's/[^[:print:]]//g' $(EXPECTED_OUTPUT_FILE)
	@diff -i ProjectManagement/tmp ProjectManagement/$(EXPECTED_OUTPUT_FILE) && echo 'PM SUCCESS' || $(call logpmerror)
pmclean:
	-@rm ProjectManagement/tmp -f
	-@rm ProjectManagement/*.class -f


clean: trieclean pqclean rbtreeclean pmclean
	-@rm -f Util/*.class
	@echo "Done"

trietestcase:
	@make triebuild
	@$(JAVA) Trie.TrieDriverCode Trie/$(INPUT_FILE) >Trie/$(EXPECTED_OUTPUT_FILE)
	@make clean
pqtestcase:
	@make pqbuild
	@$(JAVA) PriorityQueue.PriorityQueueDriverCode PriorityQueue/$(INPUT_FILE) >PriorityQueue/$(EXPECTED_OUTPUT_FILE)
	@make clean
rbtestcase:
	@make rbtreebuild
	@$(JAVA) RedBlack.RedBlackDriverCode RedBlack/$(INPUT_FILE) >RedBlack/$(EXPECTED_OUTPUT_FILE)
	@make clean
pmtestcase:
	@make pmbuild
	@$(JAVA) ProjectManagement.Scheduler_Driver ProjectManagement/$(INPUT_FILE) >ProjectManagement/$(EXPECTED_OUTPUT_FILE)
	@make clean
