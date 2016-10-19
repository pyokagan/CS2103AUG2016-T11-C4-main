#!/usr/bin/env python3
from panflute import *
import re

"""
Pandoc filter that causes the content of '<!-- BEGIN LATEX'
and 'END LATEX -->' to be converted into a raw LateX block.
"""

BEGIN_LATEX = '<!-- BEGIN LATEX'
END_LATEX = 'END LATEX -->'


def latexonly(elem, doc):
    if type(elem) is not RawBlock or elem.format != 'html':
        return
    if elem.text.startswith(BEGIN_LATEX) and elem.text.endswith(END_LATEX):
        content = elem.text[len(BEGIN_LATEX):-len(END_LATEX)]
        return RawBlock(content, format='latex')


if __name__ == '__main__':
    toJSONFilter(latexonly)
