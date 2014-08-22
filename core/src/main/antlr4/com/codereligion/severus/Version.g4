grammar Version;

version: major '.' minor '.' patch ('-' preRelease)? ('+' build)?;

major: nonNegativeInteger;
minor: nonNegativeInteger;
patch: nonNegativeInteger;
preRelease: identifier ('.' identifier)*;
build: buildIdentifier ('.' buildIdentifier)*;

nonNegativeInteger: '0' | positiveInteger;

identifier: nonNegativeInteger | nonNumericIdentifier;
buildIdentifier: identifierCharacter buildIdentifier*;

nonNumericIdentifier: buildIdentifier* NonDigitCharacter buildIdentifier*;

positiveInteger: PositiveDigit digit*; 
identifierCharacter: NonDigitCharacter | digit;
digit: '0' | PositiveDigit;

PositiveDigit: [1-9];
NonDigitCharacter: [A-Za-z] | '-';
