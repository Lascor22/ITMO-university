grammar Generator;

@header {
package antlr;

import generator.Grammar;
import generator.domain.*;
}

start returns [Grammar g]
@init {
    $g = new Grammar();
}: program[$g] EOF;

program[Grammar g]: header[$g] imports[$g] tokens[$g] startState[$g] states[$g];

header[Grammar g]: 'grammar' NAME ';' {
    $g.setName($NAME.text);
};

imports[Grammar g]: '@imp' '{' import_line[$g]* '}';

import_line[Grammar g]: ('import' import_name {$g.addImport($import_name.name.toString());} ';');

import_name returns[StringBuilder name]
@init {
$name = new StringBuilder();
}: n1=NAME {$name.append($n1.text);} ('.' n2=NAME {$name.append('.').append($n2.text);})*;

tokens[Grammar g]: '@tokens' '{' token_line[$g]* '}';

token_line[Grammar g] locals [boolean hasSkip]
@init {
    $hasSkip = false;
}: NAME ':' REGEX (skip_attr {$hasSkip = true;})? ';' {
    if ($hasSkip) {
        $g.addSkipToken($NAME.text, $REGEX.text);
    } else {
        $g.addToken($NAME.text, $REGEX.text);
    }
};

startState[Grammar g]: '@start' '=' NAME ';' {
    $g.setStartState($NAME.text);
};

skip_attr : '->' 'skip';

states[Grammar g]: '@states' '{' (state_line {$g.addState($state_line.state);})+ '}';

state_line returns [State state]
@init {
    $state = new State();
}: NAME {$state.setName($NAME.text);}
('[' parameters_state[$state] ']')? ('returns' '[' returns_state[$state] ']')?
':' r1=rule_line {$state.addRule($r1.r);} ('|' r2=rule_line {$state.addRule($r2.r);})* ';';

parameters_state[State state] : type1=NAME name1=NAME {$state.addParameter($type1.text, $name1.text);}
                               (',' type2=NAME name2=NAME {$state.addParameter($type2.text, $name2.text);})*;

returns_state[State state] : type1=NAME name1=NAME {$state.addReturn($type1.text, $name1.text);}
                               (',' type2=NAME name2=NAME {$state.addReturn($type2.text, $name2.text);})*;

rule_line returns [Rule r] locals [StringBuilder parameters, StringBuilder code]
@init {
    $r = new Rule();
    $parameters = new StringBuilder();
    $code = new StringBuilder();
}: (NAME (parameters_rule[$parameters])? (code_block[$code])?
    {$r.addItem($NAME.text, $parameters.toString(), $code.toString());
    $parameters = new StringBuilder();
    $code = new StringBuilder();})+;

parameters_rule[StringBuilder s] : '[' n1=NAME {$s.append($n1.text);} (',' n2=NAME {$s.append(", ").append($n2.text);})* ']';

code_block[StringBuilder s] : CODE_TEXT {
        $s.append($CODE_TEXT.text);
        $s.deleteCharAt(0);
        $s.deleteCharAt($s.length() - 1);
    };

WS: [ \t\n]+ -> skip;
NAME : [a-zA-Z][a-zA-Z0-9_]*;
REGEX : '"' (~('"'))* '"';
CODE_TEXT: '#' (~('#'))+ '#';
