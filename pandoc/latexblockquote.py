#!/usr/bin/env python3
from panflute import *
import re
import sys

"""
Converts blockquotes of the following form:
> Note:

to actual boxes with different colours.
"""


def latex_blockquote(elem, doc):
    if type(elem) != BlockQuote:
        return
    content = stringify(elem).strip()
    if not content.startswith('Note:'):
        return
    children = ([RawBlock(r'\begin{notebox}', format='latex')] +
                list(elem.content) +
                [RawBlock(r'\end{notebox}', format='latex')])
    return children


if __name__ == '__main__':
    toJSONFilter(latex_blockquote)
