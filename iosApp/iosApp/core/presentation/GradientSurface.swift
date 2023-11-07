//
//  GradientSurface.swift
//  iosApp
//
//  Created by Jan Kotas on 10.10.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct GradientSurface: ViewModifier {
    @Environment(\.colorScheme) var colorScheme
    
    
    func body(content: Content) -> some View {
        if colorScheme == .dark {
            let gradientStart = Shared.colors.gradientGreyStart
            let gradientEnd = Shared.colors.gradientGreyEnd
            content
                .background(
                    LinearGradient(
                        gradient: Gradient(colors: [gradientStart, gradientEnd]),
                        startPoint: .top,
                        endPoint: .bottom
                    )
                )
        } else {
            content.background(Shared.colors.surface)
        }
    }
}

extension View {
    func gradientSurface() -> some View {
        modifier(GradientSurface())
    }
}
