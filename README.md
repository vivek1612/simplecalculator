# Simple command line calculator. Supports arithmetic expressions, including operators, functions and parentheses.
# Supported operations: 
#                    add(+), subtract(-), divide(/), multiply(*), modulus(%), power(^)
# Supported functions: 
#                    sine(sin), cosine(cos), tan, log
# Known issues:
#   1. No support for negation operator (unary minus). As a work-around, use `0 - n` instead of `-n`. For example, instead of `-2^2`, use `(0-2)^2`
# Implementation outline:
#   1. Accept input from command prompt, in in-fix notation
#   2. Parse input into tokens
#   3. Convert tokens into post-fix notation, using [shunting yard algorithm](https://en.wikipedia.org/wiki/Shunting-yard_algorithm)
#   4. Build a Binary Expression Tree from post-fix tokens
#   5. Evaluate expression by traversing Binary Expression Tree
#   6. Print result to command prompt and repeat on a loop