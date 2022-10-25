# Input SOURCES,I,O,NAME,ARCH,TYPE

I           =.#
O           =$I/_tmp#
TYPE        =exe#
NAME        =hoh#
ARCH        =linux#
B           =release#


#
# External Dependencies
#
SHELL         = /bin/bash
RM            = rm -f
AS            = as
CC            = cc
CXX           = g++
CPP           = $(CC) -E
LD            = ld
ECHO          = echo
MKDIR         = mkdir
FIND          = find
RMDIR         = rmdir
LN            = ln -sfr


