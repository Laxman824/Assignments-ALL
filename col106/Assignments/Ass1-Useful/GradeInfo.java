public class GradeInfo implements GradeInfo_ {
	String grade;
	GradeInfo(String g){
		grade=g;
		
	}
    public LetterGrade grade() 
	{ 
    	LetterGrade g=LetterGrade.valueOf(grade);
    	return g;
	}
   
}
