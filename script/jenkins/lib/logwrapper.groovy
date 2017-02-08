#!/usr/bin/env groovy

def logwrapper(Closure closure){
    try{
        closure()
    }catch (Exception e){
        outputException (e)
    }
}

return this;