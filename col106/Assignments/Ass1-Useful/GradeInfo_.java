
public interface GradeInfo_ {
	   enum LetterGrade {
		A, Aminus, B, Bminus, C, Cminus, D, E, F, I; 
	   } // I is the place-holder grade for the current semester, where grade has not been earned yet 
	   public static int gradepoint(GradeInfo_.LetterGrade grade)// Returns the points earned for each letter grade
		{

			if (grade == GradeInfo_.LetterGrade.A) return 10;

			else if (grade == GradeInfo_.LetterGrade.Aminus) return 9;

			else if (grade == GradeInfo_.LetterGrade.B) return 8;

			else if (grade == GradeInfo_.LetterGrade.Bminus) return 7;

			else if (grade == GradeInfo_.LetterGrade.C) return 6;

			else if (grade == GradeInfo_.LetterGrade.Cminus) return 5;

			else if (grade == GradeInfo_.LetterGrade.D) return 4;

			else return 0;

		}

	   // I grade for every course. Override the function in implementation as needed.
	    public default LetterGrade grade()  
		{ return LetterGrade.I;
		}
	  
	}

