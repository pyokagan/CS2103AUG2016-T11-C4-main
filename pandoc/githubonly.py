#!/usr/bin/env python3
from panflute import *
import re

"""
Pandoc filter that causes everything between
'<!-- BEGIN GITHUB -->' and '<!-- END GITHUB -->'
to be ignored.  The comment lines must appear on
lines by themselves, with blank lines surrounding
them.
"""


incomment = False


def githubonly(elem, doc):
    global incomment

    if type(elem) is RawBlock and elem.format == 'html':
        if re.search(r'<!-- BEGIN GITHUB -->', elem.text):
            incomment = True
            return []
        elif re.search(r'<!-- END GITHUB -->', elem.text):
            incomment = False
            return []

    if incomment:
        return []  # suppress anything in a comment


if __name__ == '__main__':
    toJSONFilter(githubonly)
