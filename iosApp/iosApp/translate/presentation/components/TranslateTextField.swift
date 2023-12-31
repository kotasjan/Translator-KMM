//
//  TranslateTextField.swift
//  iosApp
//
//  Created by Jan Kotas on 10.10.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import UniformTypeIdentifiers

struct TranslateTextField: View {
    @Binding var fromText: String
    let toText: String?
    let isTranslating: Bool
    let fromLanguage: UiLanguage
    let toLanguage: UiLanguage
    let onTranslateEvent: (TranslateEvent) -> Void
    
    var body: some View {
        if toText == nil || isTranslating {
            IdleTextField(fromText: $fromText, isTranslating: isTranslating, onTranslateEvent: onTranslateEvent)
                .gradientSurface()
                .cornerRadius(15)
                .animation(.easeInOut, value: isTranslating)
                .shadow(radius: 4)
        } else {
            TranslatedTextView(
                fromText: fromText,
                toText: toText ?? "",
                fromLanguage: fromLanguage,
                toLanguage: toLanguage,
                onTranslateEvent: onTranslateEvent
            )
            .padding()
            .gradientSurface()
            .cornerRadius(15)
            .animation(.easeInOut, value: isTranslating)
            .shadow(radius: 4)
            .onTapGesture {
                onTranslateEvent(TranslateEvent.EditTranslation())
            }
        }
    }
}

#Preview {
    TranslateTextField(
        fromText: Binding(
            get: {"text"},
            set: {value in}
        ),
        toText: "text 2",
        isTranslating: false,
        fromLanguage: UiLanguage(language: Language.english, imageName: "english"),
        toLanguage: UiLanguage(language: Language.czech, imageName: "czech"),
        onTranslateEvent: { event in }
    )
}

private extension TranslateTextField {
    struct IdleTextField: View {
        @Binding var fromText: String
        let isTranslating: Bool
        let onTranslateEvent: (TranslateEvent) -> Void
        
        var body: some View {
            TextEditor(text: $fromText)
                .frame(
                    maxWidth: .infinity,
                    minHeight: 200,
                    alignment: .topLeading
                )
                .padding()
                .foregroundColor(Shared.colors.onSurface)
                .overlay(alignment: .bottomTrailing) {
                    ProgressButton(text: "Translate", isLoading: isTranslating, onClick: {
                        onTranslateEvent(TranslateEvent.Translate())
                    })
                }
                .padding(.trailing)
                .padding(.bottom)
                .transparentScrolling()
        }
    }
    
    struct TranslatedTextView: View {
        let fromText: String
        let toText: String
        let fromLanguage: UiLanguage
        let toLanguage: UiLanguage
        let onTranslateEvent: (TranslateEvent) -> Void
        
        private let tts = TextToSpeech()
        
        var body: some View{
            VStack(alignment: .leading) {
                LanguageDisplay(language: fromLanguage)
                Text(fromText)
                    .foregroundColor(Shared.colors.onSurface)
                HStack {
                    Spacer()
                    Button(action: {
                        UIPasteboard.general.setValue(
                            fromText,
                            forPasteboardType: UTType.plainText.identifier
                        )
                    }) {
                        Image(uiImage: UIImage(named: "copy")!)
                            .renderingMode(.template)
                            .foregroundColor(Shared.colors.lightBlue)
                        
                    }
                    Button(action: {
                        onTranslateEvent(TranslateEvent.CloseTranslation())
                    }) {
                        Image(systemName: "xmark")
                            .foregroundColor(Shared.colors.lightBlue)
                        
                    }
                }
                Divider()
                    .padding()
                LanguageDisplay(language: toLanguage)
                    .padding(.bottom)
                Text(toText)
                    .foregroundColor(Shared.colors.onSurface)
                HStack {
                    Spacer()
                    Button(action: {
                        UIPasteboard.general.setValue(
                            toText,
                            forPasteboardType: UTType.plainText.identifier
                        )
                    }) {
                        Image(uiImage: UIImage(named: "copy")!)
                            .renderingMode(.template)
                            .foregroundColor(Shared.colors.lightBlue)
                    }
                    Button(action: {
                        tts.speak(
                            text: toText,
                            language: toLanguage.language.langCode
                        )
                    }) {
                        Image(systemName: "speaker.wave.2")
                            .foregroundColor(Shared.colors.lightBlue)
                    }
                }
            }
        }
    }
}
