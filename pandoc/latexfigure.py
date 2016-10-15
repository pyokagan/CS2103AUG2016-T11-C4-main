#!/usr/bin/env python3
from panflute import *
import re
import bs4

"""
Converts images represented in HTML blocks of
<figure>
<img src="...">
<figcaption>Figure caption</figcaption>
</figure>
into proper LaTeX figures.
"""


def latex_figure(elem, doc):
    if type(elem) != RawBlock or elem.format != 'html':
        return
    root = bs4.BeautifulSoup(elem.text, 'html.parser')
    if not root.figure:
        return
    ltx = [r'\begin{figure}[H]', '\centering']
    if root.figure.img:
        src = root.figure.img['src']
        ltx.append(r'\includegraphics[width=\textwidth,height=\textheight,keepaspectratio]{'
                   + src + '}')
    if root.figure.figcaption:
        caption = root.figure.figcaption.get_text().strip()
        match = re.match(r'^Figure [\d.]+:\s+', caption)
        if match:
            caption = caption[match.end():]
        ltx.append(r'\caption{' + caption + '}')
    ltx.append(r'\end{figure}')
    return RawBlock('\n'.join(ltx), format='latex')


if __name__ == '__main__':
    toJSONFilter(latex_figure)
