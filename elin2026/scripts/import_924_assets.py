#!/usr/bin/env python3
"""Import the web assets from the supplied NODIN Android 9.24 source overlay."""
from __future__ import annotations
import argparse
import shutil
import tempfile
import zipfile
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DEST = ROOT / "app" / "src" / "main" / "assets"

def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("source_zip", type=Path)
    args = parser.parse_args()
    if not args.source_zip.is_file():
        raise SystemExit(f"Source archive not found: {args.source_zip}")
    with tempfile.TemporaryDirectory(prefix="elin2026-924-") as tmp:
        tmp_path = Path(tmp)
        with zipfile.ZipFile(args.source_zip) as archive:
            bad = archive.testzip()
            if bad:
                raise SystemExit(f"Corrupt ZIP member: {bad}")
            prefix = "android-update/assets/"
            names = [n for n in archive.namelist() if n.startswith(prefix) and not n.endswith("/")]
            if not any(n == prefix + "index.html" for n in names):
                raise SystemExit("9.24 archive is missing android-update/assets/index.html")
            extracted = tmp_path / "assets"
            for name in names:
                relative = Path(name[len(prefix):])
                if relative.is_absolute() or ".." in relative.parts:
                    raise SystemExit(f"Unsafe archive path: {name}")
                target = extracted / relative
                target.parent.mkdir(parents=True, exist_ok=True)
                target.write_bytes(archive.read(name))
        if DEST.exists():
            shutil.rmtree(DEST)
        DEST.parent.mkdir(parents=True, exist_ok=True)
        shutil.copytree(extracted, DEST)
    print(f"Imported {len(names)} files into {DEST}")

if __name__ == "__main__":
    main()
