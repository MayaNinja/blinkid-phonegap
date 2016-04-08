//
//  PPOcrRecognizerSettings.h
//  PhotoPayFramework
//
//  Created by Jura on 03/11/14.
//  Copyright (c) 2014 MicroBlink Ltd. All rights reserved.
//

#import "PPRecognizerSettings.h"

#import "PPOcrParserFactory.h"

// Parser for raw text
#import "PPRawOcrParserFactory.h"

// Regex parser
#import "PPRegexOcrParserFactory.h"

// Generic Parsers
#import "PPDateOcrParserFactory.h"
#import "PPEmailOcrParserFactory.h"
#import "PPIbanOcrParserFactory.h"
#import "PPPriceOcrParserFactory.h"

NS_ASSUME_NONNULL_BEGIN

PP_CLASS_AVAILABLE_IOS(6.0) @interface PPOcrRecognizerSettings : PPRecognizerSettings

- (instancetype)init;

- (void)addOcrParser:(PPOcrParserFactory *)factory name:(NSString *)name;

- (void)addOcrParser:(PPOcrParserFactory *)factory
                name:(NSString *)name
               group:(NSString *)group;

- (void)removeOcrParserWithName:(NSString *)name;

- (void)clearParsersInGroup:(NSString *)group;

- (void)clearAllParsers;

@end

NS_ASSUME_NONNULL_END