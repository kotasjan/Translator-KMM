//
//  TranslateHistoryItem.swift
//  iosApp
//
//  Created by Jan Kotas on 10.10.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateHistoryItem: View {
    let item: UiHistoryItem
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            VStack(alignment: .leading) {
                HStack{
                    SmallLanguageIcon(language: item.fromLanguage)
                        .padding(.trailing)
                    Text(item.fromText)
                        .foregroundColor(Shared.colors.lightBlue)
                        .font(.body)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.bottom)
                
                HStack{
                    SmallLanguageIcon(language: item.toLanguage)
                        .padding(.trailing)
                    Text(item.toText)
                        .foregroundColor(Shared.colors.onSurface)
                        .font(.body.weight(.semibold))
                }
                .frame(maxWidth: .infinity, alignment: .leading)
            }
            .frame(maxWidth: .infinity)
            .padding()
            .gradientSurface()
            .cornerRadius(15)
            .shadow(radius: 4)
        }
    }
}

#Preview {
    TranslateHistoryItem(
        item: UiHistoryItem(
            id: 0,
            fromText: "Hello",
            toText: "Ahoj",
            fromLanguage: UiLanguage(
                language: .english,
                imageName: "english"
            ),
            toLanguage: UiLanguage(
                language: .czech,
                imageName: "czech"
            )
        ), 
        onClick: {}
    )
}
