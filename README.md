<img src="./images/ThinkMachine_logo_fenix.png" width="800" alt="Think Machine" align="middle"> 

# Think Machine

[![Build Status](https://travis-ci.org/softwaremagico/ThinkMachine.svg?branch=master)](https://travis-ci.org/softwaremagico/ThinkMachine)
[![GNU GPL 3.0 License](https://img.shields.io/badge/license-GNU_GPL_3.0-brightgreen.svg)](https://github.com/softwaremagico/ThinkMachine/blob/master/license/gnugpl/license.txt)
[![Issues](https://img.shields.io/github/issues/softwaremagico/ThinkMachine.svg)](https://github.com/softwaremagico/ThinkMachine/issues)

This software generated in Java allows the creation of a character sheet for the role play game called **Fading Suns** (Revised Edition). Personally, I do not like the provided character sheet in the Player's Guide book, and I have designed a new one, based on some old files I have found ten years ago for previous versions of this game.

To get an idea of the final result, here you have a preview: 

<img src="./images/englishSheetPreview.png" width="600" alt="Fading Suns Character Sheet" align="middle"> 


If you like the design, and you want to use it, only the final PDF document is needed. To avoid the complexity of compilate this source code and using some programming languages, you can directly download the PDF from these links:
- [Character Sheet (English)](https://github.com/softwaremagico/ThinkMachine/blob/master/sheets/FadingSuns_EN.pdf)
- [Character Sheet (Spanish)](https://github.com/softwaremagico/ThinkMachine/blob/master/sheets/FadingSuns_ES.pdf)
- [Character Sheet Small Version (English)](https://github.com/softwaremagico/ThinkMachine/blob/master/sheets/FadingSuns_Small_EN.pdf)
- [Character Sheet Small Version (Spanish)](https://github.com/softwaremagico/ThinkMachine/blob/master/sheets/FadingSuns_Small_ES.pdf)

The fonts needed for these PDFs are: [ArchitectsDaughter](https://fonts.google.com/specimen/Architects+Daughter), [DejaVuSans](https://dejavu-fonts.github.io/) and [Roman Antique](http://www.steffmann.de/wordpress/). All of them can be use freely for non-commercial use. You need to download and install them.

If you want to skip the font installation, you can also donwload the sheet in PNG:
- [Character Sheet (English)](https://github.com/softwaremagico/ThinkMachine/blob/master/sheets/FadingSuns_EN-0.png)
- [Character Sheet Reverse (English)](https://github.com/softwaremagico/ThinkMachine/blob/master/sheets/FadingSuns_EN-1.png)
- [Character Sheet (Spanish)](https://github.com/softwaremagico/ThinkMachine/blob/master/sheets/FadingSuns_ES-0.png)
- [Character Sheet Reverse (Spanish)](https://github.com/softwaremagico/ThinkMachine/blob/master/sheets/FadingSuns_ES-1.png)
- [Character Sheet Small Version DIN A5 (English)](https://github.com/softwaremagico/ThinkMachine/blob/master/sheets/FadingSuns_Small_EN-0.png)
- [Character Sheet Small Version DIN A5 (Spanish)](https://github.com/softwaremagico/ThinkMachine/blob/master/sheets/FadingSuns_Small_ES-0.png)

Click any of these links to get a complete updated copy of the sheet. Still, the code of this application is provided in Github for some reasons:
* Maybe somebody wants to change some texts on the character sheet.
* Maybe somebody wants to translate it to a different language (see below for some instructions).
* In general, I like the idea of free source, and I release this software under the GNU General Public License. 
* From version 0.4.0, random characters can be generated using this application. Now is more an application that a simple PDF generator. 

## Adding a new language
If you are interested in the translation of the sheet/application in a different language, at the [Wiki](https://github.com/softwaremagico/ThinkMachine/wiki/Adding-a-new-Language) of this project you can find some instructions. 

## Execution
The application has been created using Maven with Java. Therefore, for excuting this application you need both Maven and Java installed on your machine. Then you must execute this command inside the `thinking-machine-core` folder: 

```
mvn exec:java -Dexec.args="en /path/to/file"
```
Where `en` is the language to obtain the file (now can be `en`, `es`) and `/path/to/file` must be a valid path where the application has permissions to generate a file. If this execution is too complex for you, you can always do a pull request on this project and I will generate it for you. 

You can also convert PDF to PNG automatically if you have ImageMagick installed and is at the path. Execute this Maven command:

```
mvn install -Prelease
```
And all the possible PDFs will be generated and later converted to PNG. Final result is located in the `sheet` folder. 

## Random character generation
From version 0.4.0 exists the option to generate randomly characters sheets. This feature is very usfeul for the creation of random NPC (non-player characters). You can define some basic options for the character such us nobility, psi, combat and more;  and the software will generate the complete character for you in a few seconds. Each character generated is following the rules of the Fading Suns core rule book and therefore, also can be use a playable character. I hope this feature will add extra color in your campaigns.

## Random party generation
From version 0.4.8 exists the option of autogenerating a complete group of NPC. Only must to select the maxium threat level of the group and a party schema. The application will generate a complete party from these inputs. Very useful if you want to generate encounters quickly. 

Some examples already generated are:
- [Catherine Hawkwood (English)](https://github.com/softwaremagico/ThinkMachine/blob/master/NPC/Catherine%20Hawkwood.png)
- [Shinsuke Li Halan (Spanish)](https://github.com/softwaremagico/ThinkMachine/blob/master/NPC/Shinsuke%20Li%20Halan.png)

## Final thoughts
This application only contains the logic for defining characters and NPC using Fading Suns victory point rule system. No user interface is included and therefore, cannot be use as a standalone application. In the close future new applications will appear for different platforms that includes a UI. This application will be listed here. 

## Notes
This software has been developed using the [iText library](http://itextpdf.com/) for PDF generation. 

Fading Suns is a TradeMark owned by Holistic Design. 

Fonts used in this project: DejaVuSans, ArchitectsDaughter and Roman Antique. 

[ImageMagick](https://www.imagemagick.org/script/index.php) is a free image manipulation software.


## Versions

0.1.0 Basic PDF generation

0.2.0 Multilingual added (English and Spanish).

0.3.0 Small PDF chart generation added.

0.4.0 Random character generator and equipment included.
- 0.4.1 Weapons added. 
- 0.4.2 Armours and shields added.
- 0.4.3 Combat styles added.
- 0.4.4 Age calculations
- 0.4.5 Cybernetics included. 
- 0.4.6 Random profiles.
- 0.4.7 Threat level calculation.
- 0.4.8 Random groups generation.

