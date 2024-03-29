{-# OPTIONS_GHC -w #-}
module Syntax where
import Lexer
import Control.Applicative(Applicative(..))

-- parser produced by Happy Version 1.19.4

data HappyAbsSyn t4 t5 t6 t7 t8 t9 t10
	= HappyTerminal (Token)
	| HappyErrorToken Int
	| HappyAbsSyn4 t4
	| HappyAbsSyn5 t5
	| HappyAbsSyn6 t6
	| HappyAbsSyn7 t7
	| HappyAbsSyn8 t8
	| HappyAbsSyn9 t9
	| HappyAbsSyn10 t10

action_0 (12) = happyShift action_13
action_0 (13) = happyShift action_8
action_0 (16) = happyShift action_9
action_0 (17) = happyShift action_10
action_0 (4) = happyGoto action_11
action_0 (5) = happyGoto action_2
action_0 (6) = happyGoto action_12
action_0 (7) = happyGoto action_4
action_0 (8) = happyGoto action_5
action_0 (9) = happyGoto action_6
action_0 (10) = happyGoto action_7
action_0 _ = happyFail

action_1 (13) = happyShift action_8
action_1 (16) = happyShift action_9
action_1 (17) = happyShift action_10
action_1 (5) = happyGoto action_2
action_1 (6) = happyGoto action_3
action_1 (7) = happyGoto action_4
action_1 (8) = happyGoto action_5
action_1 (9) = happyGoto action_6
action_1 (10) = happyGoto action_7
action_1 _ = happyFail

action_2 (12) = happyShift action_21
action_2 _ = happyFail

action_3 (19) = happyShift action_15
action_3 _ = happyReduce_5

action_4 (11) = happyShift action_19
action_4 (15) = happyShift action_20
action_4 _ = happyReduce_7

action_5 (14) = happyShift action_18
action_5 _ = happyReduce_9

action_6 _ = happyReduce_11

action_7 _ = happyReduce_13

action_8 _ = happyReduce_14

action_9 (13) = happyShift action_8
action_9 (16) = happyShift action_9
action_9 (17) = happyShift action_10
action_9 (9) = happyGoto action_17
action_9 (10) = happyGoto action_7
action_9 _ = happyFail

action_10 (13) = happyShift action_8
action_10 (16) = happyShift action_9
action_10 (17) = happyShift action_10
action_10 (6) = happyGoto action_16
action_10 (7) = happyGoto action_4
action_10 (8) = happyGoto action_5
action_10 (9) = happyGoto action_6
action_10 (10) = happyGoto action_7
action_10 _ = happyFail

action_11 (20) = happyAccept
action_11 _ = happyFail

action_12 (19) = happyShift action_15
action_12 (20) = happyReduce_3
action_12 _ = happyReduce_5

action_13 (13) = happyShift action_8
action_13 (16) = happyShift action_9
action_13 (17) = happyShift action_10
action_13 (6) = happyGoto action_14
action_13 (7) = happyGoto action_4
action_13 (8) = happyGoto action_5
action_13 (9) = happyGoto action_6
action_13 (10) = happyGoto action_7
action_13 _ = happyFail

action_14 _ = happyReduce_2

action_15 (13) = happyShift action_8
action_15 (16) = happyShift action_9
action_15 (17) = happyShift action_10
action_15 (5) = happyGoto action_27
action_15 (6) = happyGoto action_3
action_15 (7) = happyGoto action_4
action_15 (8) = happyGoto action_5
action_15 (9) = happyGoto action_6
action_15 (10) = happyGoto action_7
action_15 _ = happyFail

action_16 (18) = happyShift action_26
action_16 _ = happyFail

action_17 _ = happyReduce_12

action_18 (13) = happyShift action_8
action_18 (16) = happyShift action_9
action_18 (17) = happyShift action_10
action_18 (9) = happyGoto action_25
action_18 (10) = happyGoto action_7
action_18 _ = happyFail

action_19 (13) = happyShift action_8
action_19 (16) = happyShift action_9
action_19 (17) = happyShift action_10
action_19 (6) = happyGoto action_24
action_19 (7) = happyGoto action_4
action_19 (8) = happyGoto action_5
action_19 (9) = happyGoto action_6
action_19 (10) = happyGoto action_7
action_19 _ = happyFail

action_20 (13) = happyShift action_8
action_20 (16) = happyShift action_9
action_20 (17) = happyShift action_10
action_20 (8) = happyGoto action_23
action_20 (9) = happyGoto action_6
action_20 (10) = happyGoto action_7
action_20 _ = happyFail

action_21 (13) = happyShift action_8
action_21 (16) = happyShift action_9
action_21 (17) = happyShift action_10
action_21 (6) = happyGoto action_22
action_21 (7) = happyGoto action_4
action_21 (8) = happyGoto action_5
action_21 (9) = happyGoto action_6
action_21 (10) = happyGoto action_7
action_21 _ = happyFail

action_22 _ = happyReduce_1

action_23 (14) = happyShift action_18
action_23 _ = happyReduce_8

action_24 _ = happyReduce_6

action_25 _ = happyReduce_10

action_26 _ = happyReduce_15

action_27 _ = happyReduce_4

happyReduce_1 = happySpecReduce_3  4 happyReduction_1
happyReduction_1 (HappyAbsSyn6  happy_var_3)
	_
	(HappyAbsSyn5  happy_var_1)
	 =  HappyAbsSyn4
		 (Prf happy_var_1 happy_var_3
	)
happyReduction_1 _ _ _  = notHappyAtAll 

happyReduce_2 = happySpecReduce_2  4 happyReduction_2
happyReduction_2 (HappyAbsSyn6  happy_var_2)
	_
	 =  HappyAbsSyn4
		 (Prf [] happy_var_2
	)
happyReduction_2 _ _  = notHappyAtAll 

happyReduce_3 = happySpecReduce_1  4 happyReduction_3
happyReduction_3 (HappyAbsSyn6  happy_var_1)
	 =  HappyAbsSyn4
		 (Expr happy_var_1
	)
happyReduction_3 _  = notHappyAtAll 

happyReduce_4 = happySpecReduce_3  5 happyReduction_4
happyReduction_4 (HappyAbsSyn5  happy_var_3)
	_
	(HappyAbsSyn6  happy_var_1)
	 =  HappyAbsSyn5
		 ((happy_var_1:happy_var_3)
	)
happyReduction_4 _ _ _  = notHappyAtAll 

happyReduce_5 = happySpecReduce_1  5 happyReduction_5
happyReduction_5 (HappyAbsSyn6  happy_var_1)
	 =  HappyAbsSyn5
		 ([happy_var_1]
	)
happyReduction_5 _  = notHappyAtAll 

happyReduce_6 = happySpecReduce_3  6 happyReduction_6
happyReduction_6 (HappyAbsSyn6  happy_var_3)
	_
	(HappyAbsSyn7  happy_var_1)
	 =  HappyAbsSyn6
		 (Iml happy_var_1 happy_var_3
	)
happyReduction_6 _ _ _  = notHappyAtAll 

happyReduce_7 = happySpecReduce_1  6 happyReduction_7
happyReduction_7 (HappyAbsSyn7  happy_var_1)
	 =  HappyAbsSyn6
		 (Tm happy_var_1
	)
happyReduction_7 _  = notHappyAtAll 

happyReduce_8 = happySpecReduce_3  7 happyReduction_8
happyReduction_8 (HappyAbsSyn8  happy_var_3)
	_
	(HappyAbsSyn7  happy_var_1)
	 =  HappyAbsSyn7
		 (Ds happy_var_1 happy_var_3
	)
happyReduction_8 _ _ _  = notHappyAtAll 

happyReduce_9 = happySpecReduce_1  7 happyReduction_9
happyReduction_9 (HappyAbsSyn8  happy_var_1)
	 =  HappyAbsSyn7
		 (Cl happy_var_1
	)
happyReduction_9 _  = notHappyAtAll 

happyReduce_10 = happySpecReduce_3  8 happyReduction_10
happyReduction_10 (HappyAbsSyn9  happy_var_3)
	_
	(HappyAbsSyn8  happy_var_1)
	 =  HappyAbsSyn8
		 (Cn happy_var_1 happy_var_3
	)
happyReduction_10 _ _ _  = notHappyAtAll 

happyReduce_11 = happySpecReduce_1  8 happyReduction_11
happyReduction_11 (HappyAbsSyn9  happy_var_1)
	 =  HappyAbsSyn8
		 (Ng happy_var_1
	)
happyReduction_11 _  = notHappyAtAll 

happyReduce_12 = happySpecReduce_2  9 happyReduction_12
happyReduction_12 (HappyAbsSyn9  happy_var_2)
	_
	 =  HappyAbsSyn9
		 (Neg happy_var_2
	)
happyReduction_12 _ _  = notHappyAtAll 

happyReduce_13 = happySpecReduce_1  9 happyReduction_13
happyReduction_13 (HappyAbsSyn10  happy_var_1)
	 =  HappyAbsSyn9
		 (Fc happy_var_1
	)
happyReduction_13 _  = notHappyAtAll 

happyReduce_14 = happySpecReduce_1  10 happyReduction_14
happyReduction_14 (HappyTerminal (TVar happy_var_1))
	 =  HappyAbsSyn10
		 (Vr happy_var_1
	)
happyReduction_14 _  = notHappyAtAll 

happyReduce_15 = happySpecReduce_3  10 happyReduction_15
happyReduction_15 _
	(HappyAbsSyn6  happy_var_2)
	_
	 =  HappyAbsSyn10
		 (Br happy_var_2
	)
happyReduction_15 _ _ _  = notHappyAtAll 

happyNewToken action sts stk [] =
	action 20 20 notHappyAtAll (HappyState action) sts stk []

happyNewToken action sts stk (tk:tks) =
	let cont i = action i i tk (HappyState action) sts stk tks in
	case tk of {
	TImpl -> cont 11;
	TTur -> cont 12;
	TVar happy_dollar_dollar -> cont 13;
	TSym '&' -> cont 14;
	TSym '|' -> cont 15;
	TSym '!' -> cont 16;
	TSym '(' -> cont 17;
	TSym ')' -> cont 18;
	TSym ',' -> cont 19;
	_ -> happyError' (tk:tks)
	}

happyError_ 20 tk tks = happyError' tks
happyError_ _ tk tks = happyError' (tk:tks)

newtype HappyIdentity a = HappyIdentity a
happyIdentity = HappyIdentity
happyRunIdentity (HappyIdentity a) = a

instance Functor HappyIdentity where
    fmap f (HappyIdentity a) = HappyIdentity (f a)

instance Applicative HappyIdentity where
    pure    = return
    a <*> b = (fmap id a) <*> b
instance Monad HappyIdentity where
    return = HappyIdentity
    (HappyIdentity p) >>= q = q p

happyThen :: () => HappyIdentity a -> (a -> HappyIdentity b) -> HappyIdentity b
happyThen = (>>=)
happyReturn :: () => a -> HappyIdentity a
happyReturn = (return)
happyThen1 m k tks = (>>=) m (\a -> k a tks)
happyReturn1 :: () => a -> b -> HappyIdentity a
happyReturn1 = \a tks -> (return) a
happyError' :: () => [(Token)] -> HappyIdentity a
happyError' = HappyIdentity . parseError

synt tks = happyRunIdentity happySomeParser where
  happySomeParser = happyThen (happyParse action_0 tks) (\x -> case x of {HappyAbsSyn4 z -> happyReturn z; _other -> notHappyAtAll })

happySeq = happyDontSeq


parseError :: [Token] -> a
parseError _ = error "Parse error"

data Base = Prf [Expr] Expr | Expr Expr 
data Expr = Iml Tm Expr | Tm Tm  
data Tm   = Ds Tm Cl | Cl Cl 
data Cl   = Cn Cl Ng  | Ng Ng 
data Ng   = Neg Ng       | Fc Fc 
data Fc   = Vr String    | Br Expr 

instance Show Fc where
  show (Vr a) = a
  show (Br a) = show a

instance Show Ng where
  show (Neg a) = "!" ++ (show a)
  show (Fc a) = show a

instance Show Tm where
  show (Ds a b) = "(" ++ (show a) ++ " | " ++ (show b) ++ ")"
  show (Cl a)   = show a

instance Show Cl where
  show (Cn a b) = "(" ++ (show a) ++ " & " ++ (show b) ++ ")"
  show (Ng a)   = show a

instance Show Expr where
  show (Iml a b) = "(" ++ (show a) ++ " -> " ++ (show b) ++ ")"
  show (Tm a)   = show a

instance Show Base where
  show (Prf a b) = (g (map show a)) ++ "|- " ++ (show b) where
    g [] = []
    g xs = f xs
    f = foldr (\a b -> a ++ (if (null b) then " " else ", ") ++ b) []
  show (Expr a)   = show a

data Exp = Impl Exp Exp | Var String | Con Exp Exp | Dis Exp Exp | Not Exp deriving (Eq, Ord)

instance Show Exp where
  show (Impl a b) = "(" ++ (show a) ++ " -> " ++ (show b) ++ ")"
  show (Con a b) = "(" ++ (show a) ++ " & " ++ (show b) ++ ")"
  show (Dis a b) = "(" ++ (show a) ++ " | " ++ (show b) ++ ")"
  show (Not a) = "!" ++ (show a)
  show (Var a) = a
  

-- convert to my type Exp
normalize :: Base -> Exp
normalize (Expr expr) = nl0 expr where
      nl0 (Iml a b) = Impl (nl1 a) (nl0 b)
      nl0 (Tm a) = nl1 a

      nl1 (Ds a b) = Dis (nl1 a) (nl2 b)
      nl1 (Cl a) = nl2 a

      nl2 (Cn a b) = Con (nl2 a) (nl3 b)
      nl2 (Ng a) = nl3 a

      nl3 (Neg a) = Not (nl3 a)
      nl3 (Fc a) = nl4 a

      nl4 (Vr a) = Var a
      nl4 (Br a) = nl0 a
{-# LINE 1 "templates\GenericTemplate.hs" #-}
{-# LINE 1 "templates\\GenericTemplate.hs" #-}
{-# LINE 1 "<built-in>" #-}
{-# LINE 1 "<command-line>" #-}
{-# LINE 1 "templates\\GenericTemplate.hs" #-}
-- Id: GenericTemplate.hs,v 1.26 2005/01/14 14:47:22 simonmar Exp 

{-# LINE 13 "templates\\GenericTemplate.hs" #-}

{-# LINE 46 "templates\\GenericTemplate.hs" #-}








{-# LINE 67 "templates\\GenericTemplate.hs" #-}

{-# LINE 77 "templates\\GenericTemplate.hs" #-}

{-# LINE 86 "templates\\GenericTemplate.hs" #-}

infixr 9 `HappyStk`
data HappyStk a = HappyStk a (HappyStk a)

-----------------------------------------------------------------------------
-- starting the parse

happyParse start_state = happyNewToken start_state notHappyAtAll notHappyAtAll

-----------------------------------------------------------------------------
-- Accepting the parse

-- If the current token is (1), it means we've just accepted a partial
-- parse (a %partial parser).  We must ignore the saved token on the top of
-- the stack in this case.
happyAccept (1) tk st sts (_ `HappyStk` ans `HappyStk` _) =
        happyReturn1 ans
happyAccept j tk st sts (HappyStk ans _) = 
         (happyReturn1 ans)

-----------------------------------------------------------------------------
-- Arrays only: do the next action

{-# LINE 155 "templates\\GenericTemplate.hs" #-}

-----------------------------------------------------------------------------
-- HappyState data type (not arrays)



newtype HappyState b c = HappyState
        (Int ->                    -- token number
         Int ->                    -- token number (yes, again)
         b ->                           -- token semantic value
         HappyState b c ->              -- current state
         [HappyState b c] ->            -- state stack
         c)



-----------------------------------------------------------------------------
-- Shifting a token

happyShift new_state (1) tk st sts stk@(x `HappyStk` _) =
     let i = (case x of { HappyErrorToken (i) -> i }) in
--     trace "shifting the error token" $
     new_state i i tk (HappyState (new_state)) ((st):(sts)) (stk)

happyShift new_state i tk st sts stk =
     happyNewToken new_state ((st):(sts)) ((HappyTerminal (tk))`HappyStk`stk)

-- happyReduce is specialised for the common cases.

happySpecReduce_0 i fn (1) tk st sts stk
     = happyFail (1) tk st sts stk
happySpecReduce_0 nt fn j tk st@((HappyState (action))) sts stk
     = action nt j tk st ((st):(sts)) (fn `HappyStk` stk)

happySpecReduce_1 i fn (1) tk st sts stk
     = happyFail (1) tk st sts stk
happySpecReduce_1 nt fn j tk _ sts@(((st@(HappyState (action))):(_))) (v1`HappyStk`stk')
     = let r = fn v1 in
       happySeq r (action nt j tk st sts (r `HappyStk` stk'))

happySpecReduce_2 i fn (1) tk st sts stk
     = happyFail (1) tk st sts stk
happySpecReduce_2 nt fn j tk _ ((_):(sts@(((st@(HappyState (action))):(_))))) (v1`HappyStk`v2`HappyStk`stk')
     = let r = fn v1 v2 in
       happySeq r (action nt j tk st sts (r `HappyStk` stk'))

happySpecReduce_3 i fn (1) tk st sts stk
     = happyFail (1) tk st sts stk
happySpecReduce_3 nt fn j tk _ ((_):(((_):(sts@(((st@(HappyState (action))):(_))))))) (v1`HappyStk`v2`HappyStk`v3`HappyStk`stk')
     = let r = fn v1 v2 v3 in
       happySeq r (action nt j tk st sts (r `HappyStk` stk'))

happyReduce k i fn (1) tk st sts stk
     = happyFail (1) tk st sts stk
happyReduce k nt fn j tk st sts stk
     = case happyDrop (k - ((1) :: Int)) sts of
         sts1@(((st1@(HappyState (action))):(_))) ->
                let r = fn stk in  -- it doesn't hurt to always seq here...
                happyDoSeq r (action nt j tk st1 sts1 r)

happyMonadReduce k nt fn (1) tk st sts stk
     = happyFail (1) tk st sts stk
happyMonadReduce k nt fn j tk st sts stk =
      case happyDrop k ((st):(sts)) of
        sts1@(((st1@(HappyState (action))):(_))) ->
          let drop_stk = happyDropStk k stk in
          happyThen1 (fn stk tk) (\r -> action nt j tk st1 sts1 (r `HappyStk` drop_stk))

happyMonad2Reduce k nt fn (1) tk st sts stk
     = happyFail (1) tk st sts stk
happyMonad2Reduce k nt fn j tk st sts stk =
      case happyDrop k ((st):(sts)) of
        sts1@(((st1@(HappyState (action))):(_))) ->
         let drop_stk = happyDropStk k stk





             new_state = action

          in
          happyThen1 (fn stk tk) (\r -> happyNewToken new_state sts1 (r `HappyStk` drop_stk))

happyDrop (0) l = l
happyDrop n ((_):(t)) = happyDrop (n - ((1) :: Int)) t

happyDropStk (0) l = l
happyDropStk n (x `HappyStk` xs) = happyDropStk (n - ((1)::Int)) xs

-----------------------------------------------------------------------------
-- Moving to a new state after a reduction

{-# LINE 256 "templates\\GenericTemplate.hs" #-}
happyGoto action j tk st = action j j tk (HappyState action)


-----------------------------------------------------------------------------
-- Error recovery ((1) is the error token)

-- parse error if we are in recovery and we fail again
happyFail (1) tk old_st _ stk@(x `HappyStk` _) =
     let i = (case x of { HappyErrorToken (i) -> i }) in
--      trace "failing" $ 
        happyError_ i tk

{-  We don't need state discarding for our restricted implementation of
    "error".  In fact, it can cause some bogus parses, so I've disabled it
    for now --SDM

-- discard a state
happyFail  (1) tk old_st (((HappyState (action))):(sts)) 
                                                (saved_tok `HappyStk` _ `HappyStk` stk) =
--      trace ("discarding state, depth " ++ show (length stk))  $
        action (1) (1) tk (HappyState (action)) sts ((saved_tok`HappyStk`stk))
-}

-- Enter error recovery: generate an error token,
--                       save the old token and carry on.
happyFail  i tk (HappyState (action)) sts stk =
--      trace "entering error recovery" $
        action (1) (1) tk (HappyState (action)) sts ( (HappyErrorToken (i)) `HappyStk` stk)

-- Internal happy errors:

notHappyAtAll :: a
notHappyAtAll = error "Internal Happy error\n"

-----------------------------------------------------------------------------
-- Hack to get the typechecker to accept our action functions







-----------------------------------------------------------------------------
-- Seq-ing.  If the --strict flag is given, then Happy emits 
--      happySeq = happyDoSeq
-- otherwise it emits
--      happySeq = happyDontSeq

happyDoSeq, happyDontSeq :: a -> b -> b
happyDoSeq   a b = a `seq` b
happyDontSeq a b = b

-----------------------------------------------------------------------------
-- Don't inline any functions from the template.  GHC has a nasty habit
-- of deciding to inline happyGoto everywhere, which increases the size of
-- the generated parser quite a bit.

{-# LINE 322 "templates\\GenericTemplate.hs" #-}
{-# NOINLINE happyShift #-}
{-# NOINLINE happySpecReduce_0 #-}
{-# NOINLINE happySpecReduce_1 #-}
{-# NOINLINE happySpecReduce_2 #-}
{-# NOINLINE happySpecReduce_3 #-}
{-# NOINLINE happyReduce #-}
{-# NOINLINE happyMonadReduce #-}
{-# NOINLINE happyGoto #-}
{-# NOINLINE happyFail #-}

-- end of Happy Template.
