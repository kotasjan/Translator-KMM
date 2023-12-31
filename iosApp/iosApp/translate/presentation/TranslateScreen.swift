//
//  TranslateScreen.swift
//  iosApp
//
//  Created by Jan Kotas on 10.10.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateScreen: View {
    private var historyDataSource: HistoryDataSource
    private var translateUseCase: TranslateUseCase
    @ObservedObject var viewModel: IOSTranslateViewModel
    private let parser: any VoiceToTextParser
    
    init(historyDataSource: HistoryDataSource, translateUseCase: TranslateUseCase, parser: VoiceToTextParser) {
        self.historyDataSource = historyDataSource
        self.translateUseCase = translateUseCase
        self.viewModel = IOSTranslateViewModel(
            historyDataSource: historyDataSource,
            translateUseCase: translateUseCase
        )
        self.parser = parser
    }
    
    var body: some View {
        ZStack {
            ScrollView {
                HStack(alignment: .center) {
                    LanguageDropdown(
                        language: viewModel.state.fromLanguage,
                        isOpen: viewModel.state.isChoosingFromLanguage,
                        selectLanguage: { language in
                            viewModel.onEvent(event: TranslateEvent.ChooseFromLanguage(language: language))
                        }
                    )
                    Spacer()
                    SwapLanguageButton(onClick: {
                        viewModel.onEvent(event: TranslateEvent.SwapLanguages())
                    })
                    Spacer()
                    LanguageDropdown(
                        language: viewModel.state.toLanguage,
                        isOpen: viewModel.state.isChoosingToLanguage,
                        selectLanguage: { language in
                            viewModel.onEvent(event: TranslateEvent.ChooseToLanguage(language: language))
                        }
                    )
                }
                .listRowSeparator(.hidden)
                .padding(.bottom)
                
                TranslateTextField(
                    fromText: Binding(get: {viewModel.state.fromText}, set: { value in
                        viewModel.onEvent(event: TranslateEvent.ChangeTranslationText(text: value))
                    }),
                    toText: viewModel.state.toText,
                    isTranslating: viewModel.state.isTranslating,
                    fromLanguage: viewModel.state.fromLanguage,
                    toLanguage: viewModel.state.toLanguage,
                    onTranslateEvent: {
                        viewModel.onEvent(event: $0)
                    }
                )
                .listRowSeparator(.hidden)
                .padding(.bottom)
                
                if !viewModel.state.history.isEmpty {
                    Text("History")
                        .font(.title)
                        .bold()
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .listRowSeparator(.hidden)
                        .padding(.bottom)
                }

                ForEach(viewModel.state.history, id: \.self.id) { item in
                    TranslateHistoryItem(
                        item: item,
            
                        onClick: {
                            viewModel.onEvent(event: TranslateEvent.SelectHistoryItem(item: item))
                        }
                    )
                    .listRowSeparator(.hidden)
                    .padding(.bottom)
                }
            }
            .padding()
            
            VStack {
                Spacer()
                NavigationLink(
                    destination: VoiceToTextScreen(
                        onResult: { spokenText in
                            viewModel.onEvent(event: TranslateEvent.SubmitVoiceResult(result: spokenText))
                        },
                        parser: parser,
                        languageCode: viewModel.state.fromLanguage.language.langCode
                    )
                ) {
                    ZStack {
                        Circle()
                            .foregroundColor(Shared.colors.primary)
                            .padding()
                        Image(uiImage: UIImage(named: "mic")!)
                            .foregroundColor(Shared.colors.onPrimary)
                            .accessibilityIdentifier("Record audio")
                            .font(.system(size: 40.0))
                    }
                    .frame(maxWidth: 100, maxHeight: 100)
                }
            }
        }
        .onAppear {
            viewModel.startObserving()
        }
        .onDisappear {
            viewModel.dispose()
        }
    }
}
