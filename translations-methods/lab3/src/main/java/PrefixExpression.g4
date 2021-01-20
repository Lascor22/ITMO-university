grammar PrefixExpression;

expression returns[String val]: ex=expr[1] {$val = String.format("if __name__ == '__main__':\n%s", $ex.val);};

expr[int tab] returns[String val]: single=singleExpr[tab] {$val = $single.val;}
                             | multi=multiExpr[tab] {$val = $multi.val;};

multiExpr[int tab] returns[String val]: ex=singleExpr[tab] {$val = $ex.val;}
                                      | ex=singleExpr[tab] multi=multiExpr[tab] {$val = $ex.val + $multi.val;};

singleExpr[int tab] returns[String val]: ex=ifState[tab] {$val = $ex.val;}
                                       | pr=print[tab] {$val = $pr.val + "\n";}
                                       | def=define[tab] {$val = $def.val + "\n";}
                                       ;

ifState[int tab] returns[String val]: IF l=logic ex=expr[tab+1] el=elseState[tab] {$val = String.format("%sif %s:\n%s%s", "    ".repeat($tab), $l.val, $ex.val, $el.val);};

elseState[int tab] returns[String val]: ex=expr[tab+1] {$val = String.format("%selse:\n%s", "    ".repeat($tab), $ex.val);}
                                                     | {$val = "";};

print[int tab] returns[String val]: PRINT v=rightValue {$val = String.format("%sprint(%s)", "    ".repeat($tab), $v.val);};

define[int tab] returns[String val]: DEF var=VARIABLE v=rightValue {$val = String.format("%s%s = %s", "    ".repeat($tab), $var.text, $v.val);};

rightValue returns[String val]: ar=arithmetic {$val = $ar.val;}
                               | l=logic {$val = $l.val;}
                               | inp=input {$val = $inp.val;}
                               var=VARIABLE {$val = $var.text;};

logic returns[String val]: op=compareOperation left=arithmetic right=arithmetic {$val = String.format("(%s %s %s)", $left.val, $op.op, $right.val);}
                           | NOT value=logic {$val = String.format("not %s", $value.val);}
                           | TRUE {$val = "True";}
                           | FALSE {$val = "False";}
                           | op_l=logicOperation left_l=logic right_l=logic {$val = String.format("(%s %s %s)", $left_l.val, $op_l.op, $right_l.val);};

compareOperation returns[String op]: EQUALS {$op = "==";}
                                   | NOT_EQUAL {$op = "!=";}
                                   | LOWER {$op = "<";}
                                   | LOWER_EQUAL {$op = "<=";}
                                   | HIGHER {$op = ">";}
                                   | HIGHER_EQUAL {$op = ">=";};

logicOperation returns[String op]: AND {$op = "and";}
                                  | OR {$op = "or";};

arithmetic returns[String val]: op=arithmeticOperation left=arithmetic right=arithmetic {$val = String.format("(%s %s %s)", $left.val, $op.op, $right.val);}
                              | num=NUMBER {$val = $num.text;}
                              | inp=input {$val = $inp.val;}
                              | var=VARIABLE {$val = $var.text;};

input returns[String val]: inp=INPUT {$val ="input()";};

arithmeticOperation returns[String op]: PLUS {$op = "+";}
                           | MINUS {$op = "-";}
                           | MUL {$op = "*";}
                           | DIV {$op = "/";};


WHITESPACE: [ \t\r\n]+ -> skip;
fragment LETTER: [a-zA-Z];
fragment DIGIT: [0-9];
NUMBER: ('-'? [1-9] DIGIT*) | '0';

PLUS: '+';
MINUS: '-';
MUL: '*';
DIV: '/';

DEF: '=';

EQUALS: '==';
NOT_EQUAL: '!=';
LOWER: '<';
LOWER_EQUAL: '<=';
HIGHER: '>';
HIGHER_EQUAL: '>=';

AND: '&&';
OR: '||';
NOT: '!';
TRUE: 'true';
FALSE: 'false';

IF: 'if';
PRINT: 'print';
INPUT: 'input';

VARIABLE: LETTER (LETTER | DIGIT | ['])*;