open-inc changes
==========
We only add a try - catch in the JavaScript Connection to prevent Node-Red Nodes to Kill the whole Node-Red process.



Generators
==========

This repository contains documentation/bindings generators and the configs
for all Bricks and Bricklets, which describe the different language bindings.

Repository Content
------------------

language/ (c, python, etc):
 * Contains language specific IP Connection
 * and language specific documentation/bindings generators

configs/:
 * Contains the configs for all Bricks and Bricklets

generate_all.py:
 * Generates all bindings and documentations

copy_all.py:
 * Copies all bindings and documentations to the corresponding places

Usage
-----

If you only want to generate the bindings it is sufficient to clone this
repository. For example, to generate the C# bindings execute the following
commands::

  cd csharp
  python generate_csharp_bindings.py
  python generate_csharp_zip.py

If you want to generate the documentation as well, you have to clone **all**
Brick gits and **all** Bricklet gits in parallel to the generators git.
Otherwise the ``generate_all.py`` and ``copy_all.py`` scripts can't find the
examples that are used in the documentation.
