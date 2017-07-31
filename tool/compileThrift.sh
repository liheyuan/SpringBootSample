#!/bin/bash

PROJECT="my-sample"
PROJECT_CAMEL=`echo $PROJECT | perl -ne 'print s/[-]([a-z])/\u$1/gr' | perl -ne 'print s/^([a-z])/\u$1/gr'`

thrift --gen java:beans -out "$PROJECT-thrift"/src/main/java "$PROJECT-thrift"/src/main/thrift/"$PROJECT_CAMEL.thrift"
