# Elin2026 release signing

A new release keystore has been generated for Elin2026. It is intentionally not committed to Git.

- Keystore filename: `Elin2026-release.jks`
- Key alias: `elin2026release`
- Certificate SHA-256: `683FF419C418C0454619D1DD5587C8ACBBB53A96D78D9E55D84012F88EECE29C`
- Validity: 31 October 2026 to 18 March 2054

To enable signed release APKs in GitHub Actions, add these repository Actions secrets:

1. `ELIN_KEYSTORE_BASE64` — base64 encoding of the JKS file (Linux/macOS: `base64 -w0 Elin2026-release.jks`; on macOS use `base64 < Elin2026-release.jks | tr -d '\\n'`).
2. `ELIN_KEYSTORE_PASSWORD` — keystore password.
3. `ELIN_KEY_ALIAS` — `elin2026release`.
4. `ELIN_KEY_PASSWORD` — private-key password.

Do not put the JKS file, passwords, or base64 contents in source files, issues, commits, or pull requests. Keep at least two secure offline backups. Loss of the private key means future updates signed with this identity cannot be produced.

The CI workflow always builds and tests the debug variant. It builds a signed release APK only when the signing secrets are present. The release identity is separate from the legacy NODIN certificate and package.
