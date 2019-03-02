grammar Lumbergh;
WS : [ \t\r\n]+ -> skip ;
AND     : ',' | 'AND' ;
OR      : 'OR' ;
INT     : '-'? [0-9]+ ;
DOUBLE  : '-'? [0-9]+'.'[0-9]+ ;
IN      : 'IN' ;
NOTIN   : 'NOT IN';
SPACE   : ' ';
ID      : [a-zA-Z_][a-zA-Z_0-9]* ;
STRING  :  '"' (~["])* '"' | '\'' (~['])* '\''
        {
            String s = getText();
            setText(s.substring(1, s.length() - 1));
        }
        ;
EQ      : '=' '=' ? ;
LTE      : '<=' ;
GTE      : '>=' ;
NE      : '!=' ;
LT      : '<' ;
GT      : '>' ;
SEP     : '.' ;
ADD     : '+' ;
SUB     : '-' ;

SPACES
 : [ \u000B\t\r\n] -> channel(HIDDEN)
 ;

parse : ( expr_list )* EOF
     ;

expr_list
    : expr                      # Expression
    | expr AND expr             # AndExpression
    | expr OR expr              # OrExpression
    ;

expr
    : '(' condition ( condition )? ')'
    ;

condition
    : predicate                 # PredicateCondition
    | predicate ADD predicate   # AddCondition
    | predicate SUB predicate   # SubtractCondition
    | condition AND condition   # AndCondition
    | condition OR condition    # OrCondition
    ;

reference : element (SEP element)* ;

element : ID ;

predicate
    : reference operator term                                # OperatorPredicate
    | reference mathoperator term                            # MathPredicate
    | reference listoperator '(' (value (',' value)*) ')'    # InPredicate
    ;

term
    : reference
    | value
    ;

operator
    : LTE
    | GTE
    | EQ
    | NE
    | LT
    | GT
    ;

mathoperator
    : ADD
    | SUB
    ;

listoperator
    : IN
    | NOTIN
    ;

value
   : INT          # IntegerValue
   | DOUBLE       # DoubleValue
   | STRING       # StringValue
   | ID           # StringValue
   ;

K_AND : A N D;
K_IN : I N;
K_OR : O R;
K_NOT : N O T;
K_WHEN : W H E N;

fragment DIGIT : [0-9];

fragment A : [aA];
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];