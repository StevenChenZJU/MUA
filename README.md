# MUA
MakeUp Programming Language: Course Project of PPL(WK) in 2020 Fall ZJU

## Basic Logic in Executor and Interpreter
* interpreter determine what to do with the token
    1. if it is inner operation, return type(see Operation class)
    2. if it has number/bool/list/word pattern, return Operation.VALUE
    3. otherwise, check whether it can be a name, if yes, return `Operation.NAME`

then Executor apply the operation:

1. if an inner operation, first get parameter by:
    1. read token, execute it and get returned value
    2. check whether the returned value type is correct. Then **execute the operation** using lambda expression predefined
2. if an Operation.VALUE type, ask the interpreter 
     to get the complete value by using in.nextValue()
     and return the Value directly
3. if an **possible** name, check whether in namespace
    1. if in namespace and it binds a **list**,
         which means it **should be** a function call(but not definitely)
         then do what (1) do
    2. if in namespace and it binds a value other than list
         **or not in the namespace at all**
         then **return it as a word**