grammar Exam;

/*
    * Parser Rules
*/

// -------------------------------------------EXAM----------------------------------------------

exam : title header feedbacktype gradingtype openDate closeDate section+
        | question+;

title : 'TITLE:' TEXT;

header : 'HEADER:' TEXT ;

feedbacktype : 'FEEDBACK:' TEXT;

gradingtype : 'GRADINGTYPE:' TEXT;

openDate : 'OPENDATE:' TEXT;

closeDate : 'CLOSEDATE:' TEXT;

// -------------------------------------------SECTION-------------------------------------------

section : sectionheader  question+;

sectionheader : 'SECTIONHEADER:' TEXT;

// -----------------------------------------QUESTION---------------------------------------------

question : squest | mcquest | mquest | bquest | nquest | mwquest;

squest : 'SHORT_ANSWER:' TEXT 'ANSW:' TEXT 'GRADE:' TEXT
        | 'SHORTANSWER:' TEXT 'ANSW:' TEXT 'POSITION:' TEXT; // taken exam

mcquest : 'MULTIPLE_CHOICE:' TEXT 'CORANSW:' TEXT 'GRADE:' TEXT mcanswers+
        | 'MULTIPLECHOICE:' TEXT 'ANSW:' TEXT 'POSITION:' TEXT; // taken exam

mquest : 'MATCHING:' TEXT 'MATCHES:' TEXT 'GRADE:' TEXT
        | 'MATCHING:' TEXT 'MATCHES:' TEXT 'POSITION:' TEXT; // taken exam

bquest : 'BOOLEAN:' TEXT 'ANSW:' TEXT 'GRADE:' TEXT
        | 'BOOLEAN:' TEXT 'ANSW:' TEXT 'POSITION:' TEXT; // taken exam

nquest : 'NUMERICAL:' TEXT 'ANSW:' TEXT 'GRADE:' TEXT
        | 'NUMERICAL:' TEXT 'ANSW:' TEXT 'POSITION:' TEXT; // taken exam

mwquest : 'MISSING_WORD:' TEXT 'GRADE:' TEXT mwoptions+
        | 'MISSINGWORD:' TEXT mwoption+ 'POSITION:' TEXT; // taken exam

mcanswers : 'ANSW:' TEXT;

mwoptions : 'CORANSW:' TEXT mwoption+;

mwoption : 'ANSW:' TEXT;


/*
    * Lexer Rules
*/

TEXT : ~[\r\n:]+ ;

WS : [ \t\r\n]+ -> skip ;


