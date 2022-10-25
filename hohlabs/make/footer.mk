#
# Variables
#
H=$(shell echo "$(NAME)_$B_$(ARCH)_$(CC)_$(CXX)_$(CXXFLAGS)_$(CFLAGS)_$(ASFLAGS)_$(CPPFLAGS)_$(TARGET_MACH)"| md5sum | cut -d\  -f1)
X=$(patsubst %/,%,$(dir $*))#

OUTPUT_OPTION = -o $@
_SOURCES= $(SOURCES) $(SOURCES.$(ARCH)) 
OBJECTS= $(patsubst $I/%.s,  $O/%.$H.o, $(filter %.s,  $(_SOURCES))) \
         $(patsubst $I/%.S,  $O/%.$H.o, $(filter %.S,  $(_SOURCES))) \
         $(patsubst $I/%.c,  $O/%.$H.o, $(filter %.c,  $(_SOURCES))) \
         $(patsubst $I/%.cc, $O/%.$H.o, $(filter %.cc, $(_SOURCES))) \
         $(patsubst $I/%.cpp,$O/%.$H.o, $(filter %.cpp,$(_SOURCES))) \



#
#FLAGS
#
FLAGS.s.dep   = -MD -MP -MF $O/$*.s.$H.dep   -MT "$O/$*.s.$H.dep   $O/$*.$H.o" 
FLAGS.S.dep   = -MD -MP -MF $O/$*.S.$H.dep   -MT "$O/$*.S.$H.dep   $O/$*.$H.o" 
FLAGS.c.dep   = -MD -MP -MF $O/$*.c.$H.dep   -MT "$O/$*.c.$H.dep   $O/$*.$H.o" 
FLAGS.cc.dep  = -MD -MP -MF $O/$*.cc.$H.dep  -MT "$O/$*.cc.$H.dep  $O/$*.$H.o" 
FLAGS.cpp.dep = -MD -MP -MF $O/$*.cpp.$H.dep -MT "$O/$*.cpp.$H.dep $O/$*.$H.o" 

FLAGS.s       = $(FLAGS.s.dep) \
                $(ASFLAGS.$B)     $(ASFLAGS.$(ARCH))     $(ASFLAGS.$X)      $(ASFLAGS.$*.s)       \
                $(TARGET_MACH.$B) $(TARGET_MACH.$(ARCH)) $(TARGET_MACH.$X)  $(TARGET_MACH.$*.s)   
FLAGS.S       = $(FLAGS.S.dep) \
                $(ASFLAGS.$B)     $(ASFLAGS.$(ARCH))     $(ASFLAGS.$X)      $(ASFLAGS.$*.S)       \
                $(CPPFLAGS.$B)    $(CPPFLAGS.$(ARCH))    $(CPPFLAGS.$X)     $(CPPFLAGS.$*.S)      \
                $(TARGET_MACH.$B) $(TARGET_MACH.$(ARCH)) $(TARGET_MACH.$X)  $(TARGET_MACH.$*.S)   
FLAGS.c       = $(FLAGS.c.dep) \
                $(CFLAGS.$B)      $(CFLAGS.$(ARCH))      $(CFLAGS.$X)       $(CFLAGS.$*.c)        \
                $(CPPFLAGS.$B)    $(CPPFLAGS.$(ARCH))    $(CPPFLAGS.$X)     $(CPPFLAGS.$*.c)      \
                $(TARGET_ARCH.$B) $(TARGET_ARCH.$(ARCH)) $(TARGET_ARCH.$X)  $(TARGET_MACH.$*.c) 
FLAGS.cc      = $(FLAGS.cc.dep)  \
                $(CXXFLAGS.$B)    $(CXXFLAGS.$(ARCH))    $(CXXFLAGS.$X)     $(CXXFLAGS.$*.cc)     \
                $(CPPFLAGS.$B)    $(CPPFLAGS.$(ARCH))    $(CPPFLAGS.$X)     $(CPPFLAGS.$*.cc)     \
                $(TARGET_ARCH.$B) $(TARGET_ARCH.$(ARCH)) $(TARGET_ARCH.$X)  $(TARGET_MACH.$*.cc) 
FLAGS.cpp     = $(FLAGS.cpp.dep) \
                $(CXXFLAGS.$B)    $(CXXFLAGS.$(ARCH))    $(CXXFLAGS.$X)     $(CXXFLAGS.$*.cpp)    \
                $(CPPFLAGS.$B)    $(CPPFLAGS.$(ARCH))    $(CPPFLAGS.$X)     $(CPPFLAGS.$*.cpp)    \
                $(TARGET_ARCH.$B) $(TARGET_ARCH.$(ARCH)) $(TARGET_ARCH.$X)  $(TARGET_MACH.$*.cpp)

#
# LINK Command line
#
LINK.o        = $(CXX) $(LDFLAGS)

#
# COMPILE Command line
#
COMPILE.s     = $(AS)     $(ASFLAGS)              $(TARGET_MACH) $(FLAGS.s)  
COMPILE.S     = $(CXX) -c $(ASFLAGS)  $(CPPFLAGS) $(TARGET_MACH) $(FLAGS.S)  
COMPILE.c     = $(CC)  -c $(CFLAGS)   $(CPPFLAGS) $(TARGET_ARCH) $(FLAGS.c)  
COMPILE.cc    = $(CXX) -c $(CXXFLAGS) $(CPPFLAGS) $(TARGET_ARCH) $(FLAGS.cc) 
COMPILE.cpp   = $(CXX) -c $(CXXFLAGS) $(CPPFLAGS) $(TARGET_ARCH) $(FLAGS.cpp)


## Target Empty rules
#$O/%.$H.out:
#$O/%.$H.a:
#$O/%.$H.o:
#$O/%.$H.dep:
## Source Empty rules
#$I/%.h:
#$I/%.s:
#$I/%.S:
#$I/%.c:
#$I/%.cc:
#$I/%.cpp:


#
# targets
#
.PHONY: default
default: $(TYPE)

.PHONY: exe
exe: $O/$(NAME).$H.exe
	@$(LN) -sfr $< $O/$(NAME).exe

.PHONY: clean
clean:
	-$(FIND) $O -iname \*.o -or -iname \*.a -or -iname \*.exe -or -iname \*.dep 2>/dev/null | xargs $(RM) 2>/dev/null || exit 0
	-$(FIND) $O -depth -type d 2>/dev/null | xargs $(RMDIR) 2>/dev/null || exit 0

.PHONY: show
show: 
	echo xx: $(VAR) '=' $($(VAR))

.PHONY: tags
tags: 
	cd $I; git ls-files | ctags -L -

$O/$(NAME).$H.exe: $(OBJECTS) $(LINK.script)
	@$(LINK.o) $(OBJECTS) $(LOADLIBES) $(LDLIBS) $(OUTPUT_OPTION)

$O/$(NAME).$H.a: $(OBJECTS)
	@$(AR) rvs $@ $(OBJECTS)



$O/%.o : $O/%.$H.o
	@$(LN) -sfr $< $@

#
# Rules
#
$O/%.$H.o: $I/%.s
	@$(MKDIR) -p $(dir $@)
	@$(COMPILE.s) $< $(OUTPUT_OPTION)
$O/%.$H.o: $I/%.S
	@$(MKDIR) -p $(dir $@)
	@$(COMPILE.S) $< $(OUTPUT_OPTION)
$O/%.$H.o: $I/%.c
	@$(MKDIR) -p $(dir $@)
	@$(COMPILE.c)   $< $(OUTPUT_OPTION)
$O/%.$H.o: $I/%.cc
	@$(MKDIR) -p $(dir $@)
	@$(COMPILE.cc)  $< $(OUTPUT_OPTION)
$O/%.$H.o: $I/%.cpp
	@$(MKDIR) -p $(dir $@)
	@$(COMPILE.cpp) $< $(OUTPUT_OPTION)

#
# Include Dependencies
#
-include $(patsubst $I/%.s,  $O/%.s.$H.dep,  $(filter %.s,  $(_SOURCES))) \
         $(patsubst $I/%.S,  $O/%.S.$H.dep,  $(filter %.S,  $(_SOURCES))) \
         $(patsubst $I/%.c,  $O/%.c.$H.dep,  $(filter %.c,  $(_SOURCES))) \
         $(patsubst $I/%.cc, $O/%.cc.$H.dep, $(filter %.cc, $(_SOURCES))) \
         $(patsubst $I/%.cpp,$O/%.cpp.$H.dep,$(filter %.cpp,$(_SOURCES)))




#
# Doc
#
#
_IMAGES= $(patsubst %.dia,  %.eps, $(filter %.dia,  $(IMAGES))) 

IMG=$O/make/util/img.exe


image_dep: $(_IMAGES)

img: $(IMG)

%.md: %.Rmd
	R -e "library(knitr); knit(\"$*.Rmd\")"

_PANDOC=pandoc -N -f markdown+definition_lists+multiline_tables+grid_tables+all_symbols_escapable

%.beamerposter.tex: %.md image_dep img
	$(_PANDOC) --base-header-level=6 --toc --filter $(IMG) -t beamer -V theme:Warsaw  $< -o $@
	sed -i -e 's/\\begin{frame}{\(.*\)}/\\begin{column}{\1\\textwidth}/' -e 's/\\end{frame}/\\end{column}/' $@


%.beamerposter.pdf: %.md image_dep img
	$(_PANDOC) --base-header-level=6 --toc --filter $(IMG) -t beamer -V theme:Warsaw  $< -o $@



%.beamer.tex: %.md image_dep img
	$(_PANDOC) --toc --filter $(IMG) -t beamer -V theme:Warsaw  $< -o $@

%.beamer.pdf: %.md image_dep img
	$(_PANDOC) --toc --filter $(IMG) -t beamer -V theme:Warsaw  $< -o $@

%.pdf: %.md image_dep img
	$(_PANDOC)  --filter $(IMG) $< -o $@

%.tex: %.md image_dep img
	$(_PANDOC)  --filter $(IMG) $< -o $@

%.html: %.md image_dep img
	$(_PANDOC)  --filter $(IMG) $< -o $@

%.eps : %.dia 
	dia -t eps $*.dia -e $*.eps


img_dep:
	cabal install SHA gitit pandoc-types

$O/%.exe : $I/%.hs
	@$(MKDIR) -p $(dir $@)
	@ghc --make $< -o $@
	

