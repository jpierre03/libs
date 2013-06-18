# console-color

Preprocessor pure C macros to start a GNU/Linux console line with colored message.
This message can be :

* a single char array

	OK("sample);
	NOK("sample);
	INFO("sample);

* a complex formatted string. This feature use variable args definition of printf

	OK_("complex ok with args interpretations ", "|%s|%d|%x|\n", string, zero, hexa);

# How to use it ?

Just include ``jp_color.h`` and you're done.

## Need an example ?

	make && ./jp_color

# Requirements

To use it you need a standard C toolchain

* ``make``
* a preprocessor/compilator. ``gcc`` is fine

