if [[ $# -ne 2 ]]; then
  echo "Need INPUT_FILE and EXPECTED_OUTPUT_FILE"
  echo "Eg: make INPUT_FILE=INP EXPECTED_OUTPUT_FILE=EXPECTED_OUTPUT"
  exit 1
fi

score=0
make INPUT_FILE=$1 EXPECTED_OUTPUT_FILE=$2 JAVAC=javac8 JAVA=java8 >tmp
ts=$(cat tmp | grep "TRIE SUCCESS" | wc -l)
if [[ $ts -eq 1 ]]; then score=$((score + 1)); fi
ts=$(cat tmp | grep "RBTREE SUCCESS" | wc -l)
if [[ $ts -eq 1 ]]; then score=$((score + 1)); fi
ts=$(cat tmp | grep "PQ SUCCESS" | wc -l)
if [[ $ts -eq 1 ]]; then score=$((score + 1)); fi
ts=$(cat tmp | grep "PM SUCCESS" | wc -l)
if [[ $ts -eq 1 ]]; then score=$((score + 2)); fi
echo $score
