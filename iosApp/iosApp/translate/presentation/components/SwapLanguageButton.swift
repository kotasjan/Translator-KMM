//
//  SwapLanguageButton.swift
//  iosApp
//
//  Created by Jan Kotas on 10.10.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SwapLanguageButton: View {
    var onClick: () -> Void
     var body: some View {
         Button(action: onClick){
         Image(uiImage: UIImage(named: "swap_languages")!)
                 .padding()
                 .background(Color.primaryColor)
                 .clipShape(Circle())
         }
     }
}

#Preview {
    SwapLanguageButton(onClick: {})
}
